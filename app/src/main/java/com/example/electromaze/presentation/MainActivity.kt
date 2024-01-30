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
import androidx.compose.ui.Modifier
import com.example.electromaze.ViewModel
import com.example.electromaze.presentation.screens.MainScreen
import com.example.electromaze.ui.theme.ElectroMazeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ViewModel by viewModels()
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        isGranted: Boolean ->
            Log.d("Permission","permission granted:$isGranted")
        }
    private val bLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {it->
            Log.d("Bluetooth", ":${it.resultCode}: ${it.data} ")
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        viewModel.bController.registerBStateReceiver()
        bLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        setContent {

            ElectroMazeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel,bLauncher)
                    }
                }
            }
        }

    private fun requestPermissions(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {

            if (!viewModel.bController.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
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

    override fun onStop() {
        super.onStop()
       // viewModel.gController.onStopListening()
    }
}


