package com.example.electromaze.presentation.screens

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.electromaze.ViewModel
import com.example.electromaze.presentation.NavConstants

@Composable
fun MainScreen(viewModel: ViewModel,bLauncher:ActivityResultLauncher<Intent>) {
    val navController = rememberNavController()
    val st = viewModel._screen.collectAsState(initial = NavConstants.DEVICE_SCREEN)
    val pairedDevices = viewModel._bPairedDevices.collectAsState(initial = emptySet())
    val scannedDevices = viewModel._bScannedDevices.collectAsState(initial = emptySet())
    val isEnabled = viewModel._isEnabled.collectAsState(false)
    val image = viewModel._image.collectAsState(null)
    NavHost(navController = navController, startDestination = st.value) {
        composable(NavConstants.DEVICE_SCREEN) {
            ScreenConnect(isEnabled.value,pairedDevices.value,scannedDevices.value,{event->
                viewModel.startScreenEvent(event)
            }){
                bLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            }
        }
        composable(NavConstants.CONNECTING_SCREEN) {
            ConnectScreen(){

            }
        }
        composable(NavConstants.MODE_SCREEN){
            image.value?.also{
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription ="table picture",
                    //contentScale = ContentScale.Fit,
                )
                Canvas(Modifier.fillMaxSize(),onDraw = {
                    //drawImage(it.asImageBitmap())
                    drawCircle(
                        color = Color.Green,
                        radius = 30F,
                        center = Offset(x = 100.dp.toPx(), y = 100.dp.toPx())
                    )
                })
            }


        }
    }

}