package com.example.space_dash_amit_shlomo_01.logic

data class Obstacle(
    val lane: Int, // 0 = left, 1 = center, 2 = right
    var yPosition: Int
)
