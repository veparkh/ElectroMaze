package com.example.electromaze.data.bluetooth

import android.bluetooth.BluetoothSocket
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.util.Log
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.nio.ByteBuffer
import java.util.zip.CRC32

data class Angles(val roll:Float,val pitch:Float)
@OptIn(ExperimentalStdlibApi::class)
@ExperimentalUnsignedTypes
class DataExchange() {
    var currentClientSocket: BluetoothSocket? = null

    val autoControlCommand = addCRC(ubyteArrayOf(255u,1u).toByteArray())
    val manualControlCommand = addCRC( ubyteArrayOf(255u,2u).toByteArray())
    val mapBuildingCommand = addCRC(ubyteArrayOf(255u,3u).toByteArray())
    val chooseModeCommand = addCRC(ubyteArrayOf(255u,4u).toByteArray())
    val anglesCommand = byteArrayOf(100,1)
    fun countCRC(array: ByteArray): UInt {
        val CRC = CRC32()
        CRC.reset()
        CRC.update(array)
        return CRC.value.toUInt()
    }

    fun isCorrectCRC(array: ByteArray): Boolean {
        val dataBytes = array.take(array.size - 4)
        val crcBytes = array.takeLast(4)
        val bb = ByteBuffer.wrap(crcBytes.toByteArray())
        val crc = bb.int.toUInt()
        return crc == countCRC(dataBytes.toByteArray())
    }

    fun addCRC(array: ByteArray): ByteArray {
        val crc = countCRC(array)
        val bufferSize = Int.SIZE_BYTES
        val buffer = ByteBuffer.allocate(bufferSize)
        buffer.putInt(crc.toInt())
        return array + buffer.array()
    }


    fun anglesToByteArrayWithCRC(angles:Angles): ByteArray {
        val bArr = ByteArray(2 * 4)
        ByteBuffer.wrap(bArr, 0, 4).putFloat(angles.roll)
        ByteBuffer.wrap(bArr, 4, 4).putFloat(angles.pitch)
        return addCRC(anglesCommand+bArr)
    }

    fun byteArrayToCoordinates(bArr: ByteArray): Point {
        val fArr = FloatArray(2)
        for (i in fArr.indices) {
            //Log.d("Bluetooth", "byteArrayToCoordinates: ${bArr.toHexString()}")
            fArr[i] = ByteBuffer.wrap(bArr, i * 4, 4).float;
        }
        return Point(fArr[0].toInt(),fArr[1].toInt())
    }

    suspend fun sendAndReceive(arr:ByteArray){
        currentClientSocket?.let { socket->
            socket.outputStream.write(arr)
            val buffer = ByteArray(200)
            try {
            withTimeout(3000){
                while(true){
                    val length= socket.inputStream.read(buffer)
                    val answerArr = buffer.take(length).toByteArray()
                    Log.d("sendAndReceive", "answer: ${answerArr.toHexString()} ")
                    Log.d("sendAndReceive", "message : ${arr.toHexString()}")
                    if (answerArr.decodeToString().contains(arr.decodeToString()) || answerArr.contentEquals(arr)){
                        break
                }
            }
            }
            }
            catch (e:TimeoutCancellationException){
                Log.d("bluetooth", "sendAndReceive: Timeout exception")
                throw IOException()
            }
        }
    }
    fun receiveImage(): Bitmap?{
        var buffer = ByteArray(20)
        currentClientSocket?.let { socket->
            val length = socket.inputStream.read(buffer,0,10)
            if (length!=10){
                throw IOException()
            }
            val imageInfo = buffer.take(10).toByteArray()
            if(!isCorrectCRC(imageInfo)){
                throw IOException()
            }
            val bb = ByteBuffer.wrap(imageInfo.take(6).takeLast(4).toByteArray())
            val imageSize = bb.int.toLong()
            Log.d("Bluetooth", "receiveImage: size $imageSize")
            var byteCount = 0L
            var sumBuffer = ByteArray(0)
            buffer = ByteArray(60000)
            while(byteCount !=imageSize) {
                val newBytes = socket.inputStream.read(buffer,0,(imageSize - byteCount).toInt())
                byteCount += newBytes
                sumBuffer += buffer.take(newBytes)
            }
            val options = BitmapFactory.Options()
            Log.d("autoControlMode", "connectToDevice: All bytes size:"+sumBuffer.size)
            options.inJustDecodeBounds = true
            val img = BitmapFactory.decodeByteArray(sumBuffer,0,byteCount.toInt())
            Log.d("autoControlMode", "autoControlMode: img $img")
            return img
        }
        return null
    }
}
