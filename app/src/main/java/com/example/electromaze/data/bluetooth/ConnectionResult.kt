package com.example.electromaze.data.bluetooth

import android.graphics.Bitmap

sealed interface ConnectionResult {
    object connecionEstablished:ConnectionResult
    object connectionFailed:ConnectionResult
    data class newImage(val img:Bitmap):ConnectionResult
}