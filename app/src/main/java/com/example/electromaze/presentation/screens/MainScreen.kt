package com.example.electromaze.presentation.screens

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.electromaze.ViewModel
import com.example.electromaze.presentation.NavConstants
import com.example.electromaze.presentation.Screens

@Composable
fun MainScreen(viewModel: ViewModel,bLauncher:ActivityResultLauncher<Intent>) {
    val navController = rememberNavController()
    val st = viewModel._screen.collectAsState(initial = NavConstants.DEVICE_SCREEN)
    val pairedDevices = viewModel._bPairedDevices.collectAsState(initial = emptySet())
    val scannedDevices = viewModel._bScannedDevices.collectAsState(initial = emptySet())
    val isEnabled = viewModel._isEnabled.collectAsState(false)
    val image = viewModel._image.collectAsState(null)
    val coord = viewModel._coord.collectAsState(null)
    val angles = viewModel.gController._angles.collectAsState(null)
    NavHost(navController = navController, startDestination = st.value) {
        composable(NavConstants.DEVICE_SCREEN) {
            BackHandler(true) {
                viewModel.onBackButtonPressed(Screens.DEVICE_SCREEN)
            }
            ScreenConnect(isEnabled.value,pairedDevices.value,scannedDevices.value,{event->
                viewModel.startScreenEvent(event)
            }){
                viewModel.bController.updatePairedDevices()
                bLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            }
        }
        composable(NavConstants.CONNECTING_SCREEN) {
            ConnectScreen(){
                viewModel.onBackButtonPressed(Screens.CONNECTING_SCREEN)
            }
        }
        composable(NavConstants.MODE_CHOICE_SCREEN){
            ModeChoiceScreen({
                viewModel.onBackButtonPressed(Screens.MODE_CHOICE_SCREEN)
            }){mode->
                viewModel.onModeChoiceEvent(mode)
            }
        }
        composable(NavConstants.AUTO_CONTROL_SCREEN) {
            AutoModeScreen(image = image, coord = coord){
                viewModel.onBackButtonPressed(Screens.AUTO_CONTROL_SCREEN)
            }
        }
        composable(NavConstants.MANUAL_CONTROL_SCREEN) {
            ManualModeScreen(angles) {
                viewModel.onBackButtonPressed(Screens.MANUAL_CONTROL_SCREEN)
            }
        }
        composable(NavConstants.MAP_BUILDING_SCREEN) {
            MapBuildingModeScreen() {
                viewModel.onBackButtonPressed(Screens.MAP_BUILDING_SCREEN)
            }
        }
    }
}