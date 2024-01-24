@file:OptIn(ExperimentalUnsignedTypes::class)

package com.example.electromaze.data.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
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
import java.util.UUID
import javax.inject.Inject

@OptIn(ExperimentalUnsignedTypes::class)
class BluetoothController @Inject constructor(@ApplicationContext val context: Context) {

    val bManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
    val bAdapter: BluetoothAdapter? = bManager.adapter
    @OptIn(ExperimentalUnsignedTypes::class)
    val bDataExchange = DataExchange()
    val isEnabled = MutableStateFlow(bAdapter?.isEnabled?:false)
    val pairedDevices = MutableStateFlow(emptySet<BluetoothDevice>())
    val scannedDevices = MutableStateFlow(emptySet<BluetoothDevice>())



    init {
        registerBStateReceiver()
        updatePairedDevices()
    }

    @SuppressLint("MissingPermission")
    val bStateReceiver = BluetoothStateReceiver({closeConnection()}){ isEnable ->
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

    @OptIn(ExperimentalStdlibApi::class, ExperimentalUnsignedTypes::class)
    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice): Flow<ConnectionResult> {
        return flow {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                throw SecurityException("No BLUETOOTH_CONNECT permission")
            }

            bDataExchange.currentClientSocket = bAdapter?.getRemoteDevice(device.address)?.createRfcommSocketToServiceRecord(
                UUID.fromString(SERVICE_UUID)
            )
            stopDiscovery()
            bDataExchange.currentClientSocket?.let { socket ->
                try {
                    socket.connect()
                    emit(ConnectionResult.connecionEstablished)
                    Log.d("TAG", "connectToDevice: connected")
                } catch(e: IOException) {
                    socket.close()
                    bDataExchange.currentClientSocket = null
                    Log.d("TAG", "connectToDevice: IO exception")
                    emit(ConnectionResult.connectionFailed)
                }
            }
        }.flowOn(Dispatchers.IO)
    }
    @OptIn(ExperimentalStdlibApi::class, ExperimentalUnsignedTypes::class)
    @SuppressLint("MissingPermission")
    fun autoControlMode():Flow<AutoModeResult>{
        return flow{  stopDiscovery()
            var buffer: ByteArray
            var length: Int
            bDataExchange.currentClientSocket?.let { socket ->
                try {
                    bDataExchange.sendAndReceive(bDataExchange.autoControlCommand)
                    val img = bDataExchange.receiveImage()
                    if(img!=null){
                        emit(AutoModeResult.newImage(img))
                    }
                    else{
                        throw IOException("wrong img")
                    }
                    while (true){
                        buffer = ByteArray(40)
                        length = socket.inputStream.read(buffer,0,14)
                        val coordArr = buffer.take(length).toByteArray()
                        if (bDataExchange.isCorrectCRC(coordArr)){
                            val point = bDataExchange.byteArrayToCoordinates(coordArr.take(10).takeLast(8).toByteArray())
                            emit(AutoModeResult.NewCoordinates(point))
                        }
                    }

                } catch(e: IOException) {
                    if(socket.isConnected){
                        socket.close()
                    }
                    bDataExchange.currentClientSocket = null
                    Log.d("autoControlMode", "connectToDevice: $e")
                    emit(AutoModeResult.connectionFailed)
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun chooseMode():Flow<ChooseModeResult>{
        return flow{
            stopDiscovery()
            Log.d("currentClientSocket", "chooseMode: socket ${bDataExchange.currentClientSocket!=null} ")
            bDataExchange.currentClientSocket?.let { socket ->
                try {
                    bDataExchange.sendAndReceive(bDataExchange.chooseModeCommand)
                    emit(ChooseModeResult.connecionEstablished)
                } catch(e: IOException) {
                    if(socket.isConnected){
                        Log.d("TAG", "connectToDevice: $e")
                        emit(ChooseModeResult.connectionFailed)
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun closeConnection() {
        bDataExchange.currentClientSocket?.close()
        bDataExchange.currentClientSocket = null
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