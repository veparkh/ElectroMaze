package com.example.electromaze.bluetooth

import android.content.Context
import android.content.pm.PackageManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BluetoothController @Inject constructor(@ApplicationContext val context: Context) {





    fun hasPermission(permission:String):Boolean{
        val isGranted = context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

        return isGranted
    }
}