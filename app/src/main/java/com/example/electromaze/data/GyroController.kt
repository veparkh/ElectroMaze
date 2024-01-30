package com.example.electromaze.data

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.example.electromaze.data.bluetooth.Angles
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.math.PI


class GyroController @Inject constructor(@ApplicationContext  var context : Context):
    SensorEventListener {
    private lateinit var sensorManager:SensorManager

    private var gSensor:Sensor? = null
    private var rotationMatrix = FloatArray(9)
    private var _localAngles =  FloatArray(3)
    val isSensorExist = context.packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE)

    val angles = MutableStateFlow<Angles?>(null)
    val _angles : StateFlow<Angles?>
        get() = angles.asStateFlow()
    init {
        if(isSensorExist){
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            gSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        }
    }
    fun onStartListening(){
        if(!isSensorExist)
            return
        gSensor?.let {
            sensorManager.registerListener(this,it,SensorManager.SENSOR_DELAY_NORMAL)//TODO Попробовать другие константы SENSOR
        }
    }

    fun onStopListening(){
        if(!isSensorExist||!::sensorManager.isInitialized)
            return
        gSensor?.let {
            sensorManager.unregisterListener(this)
        }
    }
    override fun onSensorChanged(event: SensorEvent?) {
        if(!isSensorExist)
            return
        if(event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR){
             SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
             SensorManager.getOrientation(rotationMatrix, _localAngles)
            _localAngles.forEachIndexed{ i, el->
                _localAngles[i] = (el*180/ PI).toFloat()
            }
            angles.value = Angles(_localAngles[0],_localAngles[1])
            Log.d("Gyro", angles.value.toString())
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}