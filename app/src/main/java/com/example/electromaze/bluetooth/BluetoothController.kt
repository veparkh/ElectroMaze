package com.example.electromaze.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BluetoothController @Inject constructor(@ApplicationContext val context: Context) {

    val bManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
    val bAdapter: BluetoothAdapter? = bManager.adapter

    val isEnabled = MutableStateFlow(bAdapter?.isEnabled?:false)
    val pairedDevices = MutableStateFlow(emptySet<BluetoothDevice>())
    val scannedDevices = MutableStateFlow(emptySet<BluetoothDevice>())

    init {
        registerBStateReceiver()
        updatePairedDevices()
    }

    @SuppressLint("MissingPermission")
    val bStateReceiver = BluetoothStateReceiver{ isEnable ->
        isEnabled.value = isEnable
        val check = if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
            hasPermission(Manifest.permission.BLUETOOTH_CONNECT)
        }
        else{
            true
        }
        if (check){
            Log.d("TAG", "updatePairedDevices: ${pairedDevices.value}")
        }
        updatePairedDevices()
    }
    val bDeviceReceiver = BluetoothDeviceReceiver{device->
        scannedDevices.value = scannedDevices.value+device
    }
    fun registerBDeviceReceiver(){
        context.registerReceiver(bDeviceReceiver,IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
        context.registerReceiver(bDeviceReceiver,IntentFilter(BluetoothDevice.ACTION_FOUND))
        context.registerReceiver(bDeviceReceiver,IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED))
        context.registerReceiver(bDeviceReceiver,IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED))
    }
    fun unregisterBDeviceReceiver(){
        context.unregisterReceiver(bDeviceReceiver)
    }
    fun registerBStateReceiver(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.S){
            if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)){
                Log.d("TAG", "registerReceiver: doesnt have Permisson")
                return
            }
        }
        context.registerReceiver(bStateReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
    }
    fun unregisterBStateReceiver(){
        context.unregisterReceiver(bStateReceiver)
    }
    @SuppressLint("MissingPermission")
    fun startDiscovery(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.S){
            if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)){
                Log.d("TAG", "registerReceiver: doesnt have Permisson")
                return
            }
        }
        if (bAdapter?.isDiscovering == true){
            stopDiscovery()
        }

        scannedDevices.value = emptySet()
        bAdapter?.startDiscovery()
        Log.d("My"," discover:${bAdapter?.isDiscovering}")
    }
    @SuppressLint("MissingPermission")
    fun stopDiscovery(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.S){
            if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)){
                Log.d("TAG", "registerReceiver: doesnt have Permisson")
                return
            }
        }
        bAdapter?.cancelDiscovery()
    }
    fun hasPermission(permission:String):Boolean{
        val isGranted = context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        Log.d("TAG", "hasPermission: $permission is given: $isGranted")
        return isGranted
    }
    @SuppressLint("MissingPermission")
    fun updatePairedDevices(){
        val check = if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
            hasPermission(Manifest.permission.BLUETOOTH_CONNECT)
        }
        else{
            true
        }
        if (!check){
            Log.d("TAG", "updatePairedDevices: ${pairedDevices.value}")
            return
        }
        bAdapter?.bondedDevices?.also {
            if(it.isNotEmpty()){
                pairedDevices.value = it
            }
            Log.d("TAG", "updatePairedDevices: ${pairedDevices.value}")
        }
    }
    companion object {
        const val SERVICE_UUID = "da7ea4af-e574-4b01-aff5-e7ec710a0aeb"
    }
}