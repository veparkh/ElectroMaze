package com.example.electromaze.data.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class BluetoothStateReceiver(private val bluetoothStateChanged:(isEnabled:Boolean)->Unit): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
            when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
                BluetoothAdapter.STATE_OFF -> {
                    Log.d("bluetooth", "State OFF")
                    bluetoothStateChanged(false)

                }

                BluetoothAdapter.STATE_ON -> {
                    Log.d("TAG", "State ON")
                    bluetoothStateChanged(true)

                }
            }
        }
    }
}

