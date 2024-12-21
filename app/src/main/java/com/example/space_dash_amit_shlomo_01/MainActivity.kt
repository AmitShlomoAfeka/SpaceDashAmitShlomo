package com.example.space_dash_amit_shlomo_01

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd
import com.example.space_dash_amit_shlomo_01.logic.GameManager

class MainActivity : AppCompatActivity() {

    private lateinit var rocket: AppCompatImageView
    private lateinit var heartIcons: Array<AppCompatImageView>
    private lateinit var buttonLeft: ImageButton
    private lateinit var buttonRight: ImageButton
    private lateinit var mainLayout: RelativeLayout

    private lateinit var gameManager: GameManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views
        rocket = findViewById(R.id.main_IMG_rocket)
        buttonLeft = findViewById(R.id.main_button_left)
        buttonRight = findViewById(R.id.main_button_right)
        mainLayout = findViewById(R.id.main)
        heartIcons = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )

        // Initialize game manager
        gameManager = GameManager(heartIcons.size)

        // Set button click listeners
        buttonLeft.setOnClickListener {
            gameManager.moveRocketLeft()
            updateRocketPosition()
        }

        buttonRight.setOnClickListener {
            gameManager.moveRocketRight()
            updateRocketPosition()
        }

        // Start game loop
        startGameLoop()
    }

    private fun startGameLoop() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                gameManager.addObstacle()
                createAsteroidView()
                gameManager.updateObstacles()
                refreshUI()

                if (!gameManager.isGameOver) {
                    handler.postDelayed(this, 1000) // Update every second
                } else {
                    showCrashNotification()
                }
            }
        }
        handler.post(runnable)
    }

    private fun createAsteroidView() {
        val asteroid = AppCompatImageView(this)
        asteroid.setImageResource(R.drawable.asteroid_icon)

        val lane = gameManager.getRandomLane()
        val params = RelativeLayout.LayoutParams(150, 150) // Set asteroid size
        when (lane) {
            0 -> params.addRule(RelativeLayout.ALIGN_PARENT_START)
            1 -> params.addRule(RelativeLayout.CENTER_HORIZONTAL)
            2 -> params.addRule(RelativeLayout.ALIGN_PARENT_END)
        }
        asteroid.layoutParams = params
        asteroid.y = 0f // Start at the top of the screen
        mainLayout.addView(asteroid)

        val animation = ObjectAnimator.ofFloat(asteroid, "translationY", 0f, mainLayout.height.toFloat())
        animation.duration = 3000 // Set animation duration
        animation.start()

        animation.doOnEnd {
            mainLayout.removeView(asteroid)
        }
    }

    private fun updateRocketPosition() {
        val params = rocket.layoutParams as RelativeLayout.LayoutParams
        when (gameManager.getRocketPosition()) {
            0 -> params.addRule(RelativeLayout.ALIGN_PARENT_START)
            1 -> {
                params.removeRule(RelativeLayout.ALIGN_PARENT_START)
                params.removeRule(RelativeLayout.ALIGN_PARENT_END)
                params.addRule(RelativeLayout.CENTER_HORIZONTAL)
            }
            2 -> params.addRule(RelativeLayout.ALIGN_PARENT_END)
        }
        rocket.layoutParams = params
    }

    private fun refreshUI() {
        // Update lives
        for (i in heartIcons.indices) {
            heartIcons[i].visibility =
                if (i < gameManager.getRemainingLives()) View.VISIBLE else View.INVISIBLE
        }

        // Check if the game is over
        if (gameManager.isGameOver) {
            showCrashNotification()
        }
    }

    private fun vibrate() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }

        vibrator?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                it.vibrate(500)
            }
        }
    }

    private fun showCrashNotification() {
        if (gameManager.isGameOver) {
            Toast.makeText(this, "Game Over :(", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Crash!", Toast.LENGTH_SHORT).show()
        }
        vibrate()
    }
}
