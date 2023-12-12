package com.example.electromaze.presentation

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.electromaze.ViewModel
import com.example.electromaze.ui.theme.ElectroMazeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: ViewModel by viewModels()
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        isGranted: Boolean ->
            Log.d("MainActivity","permission granted:$isGranted")
        }
    private val bLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {it->
            Log.d("TAG", ":${it.resultCode}: ${it.data} ")
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        viewModel.bController.registerBStateReceiver()
        bLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        setContent {
            val navController = rememberNavController()
            val st = viewModel._screen.collectAsState(initial = "START")
            val pairedDevices = viewModel._bPairedDevices.collectAsState(initial = emptySet())
            val scannedDevices = viewModel._bScannedDevices.collectAsState(initial = emptySet())
            val isEnabled = viewModel._isEnabled.collectAsState(false)
            ElectroMazeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = st.value) {

                        composable("START") {
                            ScreenConnect(isEnabled.value,pairedDevices.value,scannedDevices.value,{},{
                                viewModel.bController.registerBDeviceReceiver()
                                viewModel.bController.startDiscovery()

                            }){
                                bLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
                                }
                            }
                        }
                    }
                }
            }
        }


    fun requestPermissions(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {

            if (!viewModel.bController.hasPermission(Manifest.permission.BLUETOOTH)) {
                requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH)
            }
            if (!viewModel.bController.hasPermission(Manifest.permission.BLUETOOTH_ADMIN)) {
                requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_ADMIN)
            }
            if (!viewModel.bController.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (!viewModel.bController.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            if (!viewModel.bController.hasPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            if (!viewModel.bController.hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
                requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_SCAN)
            }
            if (!viewModel.bController.hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
            }
        } else {
            if (!viewModel.bController.hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
                requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_SCAN)
            }
            if (!viewModel.bController.hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
            }

        }
    }
}
}

