package com.example.electromaze.data.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import java.util.Calendar
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class BluetoothController @Inject constructor(@ApplicationContext val context: Context) {

    val bManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
    val bAdapter: BluetoothAdapter? = bManager.adapter

    val isEnabled = MutableStateFlow(bAdapter?.isEnabled?:false)
    val pairedDevices = MutableStateFlow(emptySet<BluetoothDevice>())
    val scannedDevices = MutableStateFlow(emptySet<BluetoothDevice>())

    private var currentClientSocket: BluetoothSocket? = null

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

    @OptIn(ExperimentalStdlibApi::class)
    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice): Flow<ConnectionResult> {
        return flow {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                throw SecurityException("No BLUETOOTH_CONNECT permission")
            }

            currentClientSocket = bAdapter?.getRemoteDevice(device.address)?.createRfcommSocketToServiceRecord(
                UUID.fromString(SERVICE_UUID)
            )
            stopDiscovery()
            var buffer = ByteArray(1000)
            var sumBuffer = ByteArray(0)
            var byteCount = 0
            currentClientSocket?.let { socket ->
                try {
                    socket.connect()
                    emit(ConnectionResult.connecionEstablished)
                    Log.d("TAG", "connectToDevice: connected")
                    var  i=0
                    var currentTime: Date = Calendar.getInstance().time
                    while(true) {
                        do{
                            val newBytes = socket.inputStream.read(buffer)
                            byteCount += newBytes
                            sumBuffer += buffer.take(newBytes)
                            Log.d("TAG", "connectToDevice: All bytes:"+buffer.toHexString())
                            if(byteCount==27631)
                                break
                        }while(newBytes>0)
                        val options = BitmapFactory.Options()
                        Log.d("TAG", "connectToDevice: All bytes size:"+sumBuffer.size)
                        options.inJustDecodeBounds = true
                        val img = BitmapFactory.decodeByteArray(sumBuffer,0,byteCount)
                        if(img!=null){
                            emit(ConnectionResult.newImage(img))
                        }
                        else{
                            Log.d("TAG", "connectToDevice: message taken, but there is no img ")
                        }
                        Log.d("TAG", "connectToDevice: read byte count:$byteCount}")
                    }
                } catch(e: IOException) {
                    socket.close()
                    currentClientSocket = null
                    Log.d("TAG", "connectToDevice: IO exception")
                    emit(ConnectionResult.connectionFailed)
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun closeConnection() {
        currentClientSocket?.close()
        currentClientSocket = null
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