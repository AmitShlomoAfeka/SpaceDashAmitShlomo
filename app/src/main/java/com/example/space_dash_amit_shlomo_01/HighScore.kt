package com.example.space_dash_amit_shlomo_01.data
import com.google.android.gms.maps.model.LatLng

data class HighScore(
    val playerName: String,
    val score: Int,
    val location: LatLng = LatLng(0.0, 0.0)
)
