package com.example.space_dash_amit_shlomo_01.logic

import com.example.space_dash_amit_shlomo_01.data.HighScore
import com.google.android.gms.maps.model.LatLng

class GameManager(private val maxLives: Int) {

    private var currentLives = maxLives
    private var score = 0
    private var distance = 0
    var isGameOver = false
        private set

    private var rocketPosition = 2 // Default to middle lane (0-4)
    private val lanes = Array(5) { mutableListOf<Obstacle>() } // 5 lanes for obstacles

    fun moveRocketLeft() {
        if (rocketPosition > 0) rocketPosition--
    }

    fun moveRocketRight() {
        if (rocketPosition < 4) rocketPosition++
    }

    fun getRocketPosition(): Int = rocketPosition

    fun getRemainingLives(): Int = currentLives

    fun getScore(): Int = score

    fun getDistance(): Int = distance

    fun increaseScore(points: Int) {
        score += points
    }

    fun decreaseLife() {
        currentLives--
        if (currentLives <= 0) {
            isGameOver = true
        }
    }

    fun spawnObstacle() {
        val lane = getRandomLane()
        val isCoin = kotlin.random.Random.nextBoolean()
        val obstacle = Obstacle(isCoin, lane, 0f)
        lanes[lane].add(obstacle)
    }

    fun getRandomLane(): Int {
        return kotlin.random.Random.nextInt(0, lanes.size)
    }

    fun updateObstacles() {
        lanes.forEach { lane ->
            val iterator = lane.iterator()
            while (iterator.hasNext()) {
                val obstacle = iterator.next()
                obstacle.updatePosition(10)

                if (obstacle.isOutOfScreen()) {
                    iterator.remove()
                } else if (obstacle.collidesWithRocket(rocketPosition)) {
                    handleCollision(obstacle)
                    iterator.remove()
                }
            }
        }
        increaseDistance(1)
    }

    private fun handleCollision(obstacle: Obstacle) {
        if (obstacle.isCoin) {
            increaseScore(100)
        } else {
            decreaseLife()
        }
    }

    fun getObstacles(): List<Obstacle> {
        return lanes.toList().flatten()
    }


    fun increaseDistance(increment: Int) {
        distance += increment
    }

    fun resetGame() {
        currentLives = maxLives
        score = 0
        distance = 0
        isGameOver = false
        rocketPosition = 2
        lanes.forEach { it.clear() }
    }
    fun endGame(playerName: String, score: Int, location: LatLng) {
        val newHighScore = HighScore(playerName, score, location)
        HighScoresManager.addPlayerScore(newHighScore)
    }

}
