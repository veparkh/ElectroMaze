package com.example.electromaze.data

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.math.PI


class GyroController @Inject constructor(@ApplicationContext  var context : Context):
    SensorEventListener {
    private lateinit var sensorManager:SensorManager
    private var gSensor:Sensor? = null
    private var rotationMatrix = FloatArray(9)
    private var _angles =  FloatArray(3)
    val isSensorExist = context.packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE)
    val angles = MutableStateFlow(arrayOf(0.0f,0.0f,0.0f))
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
             SensorManager.getOrientation(rotationMatrix, _angles)
            _angles.forEachIndexed{i,el->
                _angles[i] = (el*180/ PI).toFloat()
            }
            angles.value = _angles.clone().toTypedArray()
            Log.d("Gyro", angles.value.contentToString())
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}