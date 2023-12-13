package com.example.electromaze.presentation

import android.app.Instrumentation.ActivityResult
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.electromaze.ViewModel

@Composable
fun MainScreen(viewModel: ViewModel,bLauncher:ActivityResultLauncher<Intent>) {
    val navController = rememberNavController()
    val st = viewModel._screen.collectAsState(initial = NavConstants.DEVICE_SCREEN)
    val pairedDevices = viewModel._bPairedDevices.collectAsState(initial = emptySet())
    val scannedDevices = viewModel._bScannedDevices.collectAsState(initial = emptySet())
    val isEnabled = viewModel._isEnabled.collectAsState(false)

    NavHost(navController = navController, startDestination = st.value) {
        composable(NavConstants.DEVICE_SCREEN) {
            ScreenConnect(isEnabled.value,pairedDevices.value,scannedDevices.value,{},{
                viewModel.bController.registerBDeviceReceiver()
                viewModel.bController.startDiscovery()

            }){
                bLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            }
        }
    }
}