package com.example.electromaze

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import com.example.electromaze.data.GyroController
import com.example.electromaze.data.bluetooth.BluetoothController
import com.example.electromaze.presentation.NavConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(val bController: BluetoothController, val gController: GyroController): ViewModel() {
    private val screen = MutableStateFlow(NavConstants.DEVICE_SCREEN)
    init {
        gController.onStartListening()
    }
    val _screen :StateFlow<String>
        get() = screen.asStateFlow()
    val _bPairedDevices :StateFlow<Set<BluetoothDevice>>
        get() = bController.pairedDevices.asStateFlow()
    val _bScannedDevices :StateFlow<Set<BluetoothDevice>>
        get() = bController.scannedDevices.asStateFlow()
    val _isEnabled = bController.isEnabled.asStateFlow()


}