package com.example.electromaze.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class BluetoothDeviceReceiver(private val deviceFound:(device:BluetoothDevice)->Unit):
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){

            BluetoothDevice.ACTION_FOUND -> {
                Log.d("TAG", "onReceive: Something found")
                val device = getDevice(intent)
                device?.let {
                    deviceFound(it)
                }
            }
            BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                val device = getDevice(intent)
                device?.let {

                }
            }
            BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                Log.d("TAG", "onReceive:  Discovery finished")

            }
        }
    }
    private fun getDevice(intent: Intent): BluetoothDevice?{
        return if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra(
                BluetoothDevice
                    .EXTRA_DEVICE, BluetoothDevice::class.java
            )
        } else {
            intent.getParcelableExtra(
                BluetoothDevice.EXTRA_DEVICE)
        }
    }
}