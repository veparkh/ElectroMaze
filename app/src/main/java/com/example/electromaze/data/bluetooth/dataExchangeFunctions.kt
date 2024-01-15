package com.example.electromaze.data.bluetooth

import java.nio.ByteBuffer
import java.util.zip.CRC32


fun countCRC(array: ByteArray):UInt{
    val CRC = CRC32()
    CRC.reset()
    CRC.update(array)
    return CRC.value.toUInt()
}

fun isCorrectCRC(array: ByteArray):Boolean{
    val dataBytes = array.take(array.size-4)
    val crcBytes = array.takeLast(4)
    println(crcBytes)
    val bb = ByteBuffer.wrap(crcBytes.toByteArray())
    val crc = bb.int.toUInt()
    return crc == countCRC(dataBytes.toByteArray())
}
fun addCRC(array: ByteArray):ByteArray{
    val crc = countCRC(array)
    val bufferSize = Int.SIZE_BYTES
    val buffer = ByteBuffer.allocate(bufferSize)
    buffer.putInt(crc.toInt())
    return array + buffer.array()
}

fun anglesToByteArrayWithCRC(fArr:FloatArray):ByteArray{

    val bArr = ByteArray(fArr.size*4)
    for (i in fArr.indices) {
        ByteBuffer.wrap(bArr, i*4, 4).putFloat(fArr[i])
    }
    return addCRC(bArr)
}

fun byteArrayToCoordinates(bArr:ByteArray):FloatArray{
    val fArr = FloatArray(2)
    for(i in fArr.indices){
        fArr[i] = ByteBuffer.wrap(bArr, i*4, 4).float;
    }
    return  fArr
}
