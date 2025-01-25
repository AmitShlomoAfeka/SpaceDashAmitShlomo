package com.example.space_dash_amit_shlomo_01.logic

class Obstacle(
    val isCoin: Boolean,
    val lane: Int,
    private var positionY: Float
) {
    companion object {
        const val SCREEN_HEIGHT = 1920f
        const val COLLISION_THRESHOLD = 150f
    }

    fun updatePosition(speed: Int) {
        positionY += speed
    }

    fun isOutOfScreen(): Boolean {
        return positionY > SCREEN_HEIGHT
    }

    fun collidesWithRocket(rocketLane: Int): Boolean {
        return rocketLane == lane && positionY >= SCREEN_HEIGHT - COLLISION_THRESHOLD
    }
}
