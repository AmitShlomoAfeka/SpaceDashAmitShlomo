package com.example.space_dash_amit_shlomo_01.logic

import kotlin.random.Random

class GameManager(private val totalLives: Int) {

    private var rocketPosition: Int = 1 // 0 = left, 1 = center, 2 = right
    private var remainingLives: Int = totalLives
    private val obstacles = mutableListOf<Obstacle>()

    val isGameOver: Boolean
        get() = remainingLives <= 0

    fun getRocketPosition(): Int {
        return rocketPosition
    }

    fun getRemainingLives(): Int {
        return remainingLives
    }

    fun moveRocketLeft() {
        if (rocketPosition > 0) rocketPosition--
    }

    fun moveRocketRight() {
        if (rocketPosition < 2) rocketPosition++
    }

    fun addObstacle() {
        val randomLane = Random.nextInt(0, 3)
        obstacles.add(Obstacle(randomLane, 0))
    }

    fun updateObstacles() {
        val iterator = obstacles.iterator()
        while (iterator.hasNext()) {
            val obstacle = iterator.next()
            obstacle.yPosition++

            // Check for collision
            if (obstacle.yPosition == 9 && obstacle.lane == rocketPosition) {
                remainingLives--
                iterator.remove()
            } else if (obstacle.yPosition > 9) {
                iterator.remove() // Remove obstacles that move out of bounds
            }
        }
    }

    fun getRandomLane(): Int {
        return Random.nextInt(0, 3)
    }

}
