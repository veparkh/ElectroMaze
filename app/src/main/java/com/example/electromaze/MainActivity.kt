package com.example.electromaze

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.electromaze.ui.theme.ElectroMazeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: ViewModel by viewModels()
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        isGranted: Boolean ->
            Log.d("MainActivity","permission granted:$isGranted")
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        setContent {
            val navController = rememberNavController()
            val st = viewModel._screen.collectAsState(initial = "START")
            ElectroMazeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = st.value) {

                        composable("START") {
                            Button(onClick = { viewModel.navigate() }) {
                                Text(text = "Нажми меня")
                            }
                        }
                        composable("STOP") {
                            Button(onClick = { viewModel.navigate() }) {
                                Text(text = "АААА")
                            }
                            Text("sfdgdghfgh")
                        }
                    }
                }
            }
        }
    }


    fun requestPermissions(){
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
}

