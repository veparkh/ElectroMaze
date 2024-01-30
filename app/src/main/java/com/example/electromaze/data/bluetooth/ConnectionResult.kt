package com.example.electromaze.data.bluetooth

import android.graphics.Bitmap
import android.graphics.Point

sealed interface ConnectionResult {
    object connecionEstablished:ConnectionResult
    object connectionFailed:ConnectionResult
}

sealed interface AutoModeResult {
    object connectionFailed:AutoModeResult
    data class newImage(val img:Bitmap):AutoModeResult
    data class NewCoordinates(val point:Point):AutoModeResult
}

sealed interface ManualModeResult {

    object connectionFailed:ManualModeResult
    data class newImage(val img:Bitmap):ManualModeResult
    data class NewAngles(val point:Point):ManualModeResult
}
sealed interface MapBuildingResult {
    object connecionEstablished:MapBuildingResult
    object connectionFailed:MapBuildingResult
    data class newImage(val img:Bitmap):MapBuildingResult
    data class NewCoordinates(val point:Point):MapBuildingResult
}
sealed interface ChooseModeResult {
    object connecionEstablished:ChooseModeResult
    object connectionFailed:ChooseModeResult

}



