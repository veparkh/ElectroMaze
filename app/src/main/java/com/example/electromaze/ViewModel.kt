package com.example.electromaze

import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import android.graphics.Point
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electromaze.data.GyroController
import com.example.electromaze.data.bluetooth.AutoModeResult
import com.example.electromaze.data.bluetooth.BluetoothController
import com.example.electromaze.data.bluetooth.ChooseModeResult
import com.example.electromaze.data.bluetooth.ConnectionResult
import com.example.electromaze.presentation.NavConstants
import com.example.electromaze.presentation.Screens
import com.example.electromaze.presentation.screens.Modes
import com.example.electromaze.presentation.screens.events.StartScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(val bController: BluetoothController, val gController: GyroController): ViewModel() {
    private val screen = MutableStateFlow(NavConstants.DEVICE_SCREEN)
    private val image = MutableStateFlow<Bitmap?>(null)
    val _image :StateFlow<Bitmap?>
        get() = image.asStateFlow()
    private val coord = MutableStateFlow<Point?>(null)
    val _coord :StateFlow<Point?>
        get() = coord.asStateFlow()
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

        screen.value = NavConstants.CONNECTING_SCREEN
        deviceConnectionJob = bController.connectToDevice(device).onEach { result->
            when(result){
                ConnectionResult.connecionEstablished -> {
                    screen.value = NavConstants.MODE_CHOICE_SCREEN
                }
                ConnectionResult.connectionFailed -> {
                    screen.value = NavConstants.DEVICE_SCREEN
                }
            }
        }.launchIn(viewModelScope)
    }
    fun onBackButtonPressed(screen:Screens){
        when(screen){
            Screens.DEVICE_SCREEN -> {
                Log.d("TAG", "onBackButtonPressed: ${screen.toString()}")
            }
            Screens.CONNECTING_SCREEN -> {

            }
            Screens.MODE_CHOICE_SCREEN -> {
                deviceConnectionJob?.cancel()
                Log.d("Bluetooth", "onBackButtonPressed: MODE_CHOICE_SCREEN")
                closeConnection()
                this.screen.value = NavConstants.DEVICE_SCREEN
            }
            Screens.MANUAL_CONTROL_SCREEN -> {
                viewModelScope.launch{
                    deviceConnectionJob?.cancelAndJoin()
                    Log.d("Bluetooth", "onBackButtonPressed: Job finished")
                    deviceConnectionJob = bController.chooseMode().onEach {result->

                    }.launchIn(viewModelScope)
                }


            }
            Screens.MAP_BUILDING_SCREEN -> {
                deviceConnectionJob?.cancel()
                deviceConnectionJob = bController.chooseMode().onEach {result->

                }.launchIn(viewModelScope)
            }
            Screens.AUTO_CONTROL_SCREEN -> {
                viewModelScope.launch {
                    deviceConnectionJob?.cancelAndJoin()
                    Log.d("Bluetooth", "onBackButtonPressed: Job finished")
                    deviceConnectionJob = bController.chooseMode().onEach {result->
                        Log.d("Bluetooth", "onBackButtonPressed: chooseMode $result")
                        when(result){
                            ChooseModeResult.connecionEstablished -> {
                                this@ViewModel.screen.value = NavConstants.MODE_CHOICE_SCREEN
                            }
                            ChooseModeResult.connectionFailed -> {
                                closeConnection()
                                bController.updatePairedDevices()
                                this@ViewModel.screen.value = NavConstants.DEVICE_SCREEN
                            }
                        }
                    }.launchIn(viewModelScope)
                }

            }
        }
    }

    fun onModeChoiceEvent(mode: Modes) {
        when (mode){
            Modes.AutoControl -> {
                if(_isEnabled.value){
                    deviceConnectionJob = bController.autoControlMode().onEach { result->
                        when(result){
                            is AutoModeResult.NewCoordinates -> {
                                coord.value = result.point
                            }
                            AutoModeResult.connectionFailed -> {

                            }
                            is AutoModeResult.newImage -> {
                                image.value = result.img
                            }
                        }
                    }.launchIn(viewModelScope)
                    screen.value = NavConstants.AUTO_CONTROL_SCREEN
                }
            }
            Modes.ManualControl -> {

            }
            Modes.MapBuilding -> {

            }
        }
    }

    private fun closeConnection(){
        image.value = null
        coord.value = null
        bController.closeConnection()

    }
}