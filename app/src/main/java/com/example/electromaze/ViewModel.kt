package com.example.electromaze

import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electromaze.data.GyroController
import com.example.electromaze.data.bluetooth.BluetoothController
import com.example.electromaze.data.bluetooth.ConnectionResult
import com.example.electromaze.presentation.NavConstants
import com.example.electromaze.presentation.screens.events.StartScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(val bController: BluetoothController, val gController: GyroController): ViewModel() {
    private val screen = MutableStateFlow(NavConstants.DEVICE_SCREEN)
    private val image = MutableStateFlow<Bitmap?>(null)
    val _image :StateFlow<Bitmap?>
        get() = image.asStateFlow()
    private var deviceConnectionJob: Job? = null
    init {
       // gController.onStartListening()
    }
    val _screen :StateFlow<String>
        get() = screen.asStateFlow()
    val _bPairedDevices :StateFlow<Set<BluetoothDevice>>
        get() = bController.pairedDevices.asStateFlow()
    val _bScannedDevices :StateFlow<Set<BluetoothDevice>>
        get() = bController.scannedDevices.asStateFlow()
    val _isEnabled = bController.isEnabled.asStateFlow()

    fun startScreenEvent(event:StartScreenEvents){
        when(event){
            is StartScreenEvents.onDeviceClick -> {
                bController.stopDiscovery()
                connectToDevice(event.device)
            }
            StartScreenEvents.onSeacrhIconClick -> {
                bController.registerBDeviceReceiver()
                bController.startDiscovery()
            }
        }
    }
    private fun connectToDevice(device:BluetoothDevice){


        deviceConnectionJob = bController.connectToDevice(device).onEach { result->
            when(result){
                ConnectionResult.connecionEstablished -> {
                    screen.value = NavConstants.MODE_SCREEN
                }
                ConnectionResult.connectionFailed -> {
                    screen.value = NavConstants.DEVICE_SCREEN
                }
                is ConnectionResult.newImage -> {
                    image.value = result.img
                }
            }
        }.launchIn(viewModelScope)


    }
}