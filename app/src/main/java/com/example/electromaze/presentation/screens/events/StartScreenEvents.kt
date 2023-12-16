package com.example.electromaze.presentation.screens.events

import android.bluetooth.BluetoothDevice

sealed class StartScreenEvents() {
        data class onDeviceClick(val device: BluetoothDevice):StartScreenEvents()
        object onSeacrhIconClick:StartScreenEvents()
}