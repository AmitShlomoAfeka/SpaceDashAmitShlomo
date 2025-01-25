package com.example.space_dash_amit_shlomo_01

import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.example.space_dash_amit_shlomo_01.logic.GameManager
import kotlin.random.Random

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var rocket: ImageView
    private lateinit var heartIcons: Array<ImageView>
    private lateinit var buttonLeft: ImageButton
    private lateinit var buttonRight: ImageButton
    private lateinit var mainLayout: RelativeLayout
    private lateinit var distanceTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var gameManager: GameManager

    private val handler = Handler(Looper.getMainLooper())
    private var useSensorMode = false

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private var gameRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rocket = findViewById(R.id.main_IMG_rocket)
        buttonLeft = findViewById(R.id.main_button_left)
        buttonRight = findViewById(R.id.main_button_right)
        mainLayout = findViewById(R.id.main)
        distanceTextView = findViewById(R.id.main_TXT_distance)
        scoreTextView = findViewById(R.id.main_TXT_score)
        heartIcons = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )

        gameManager = GameManager(heartIcons.size)

        // Ensuring the layout is ready
        mainLayout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mainLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                updateRocketPosition()
            }
        })

        useSensorMode = intent.getBooleanExtra("USE_SENSOR_MODE", false)

        if (!useSensorMode) {
            setupButtonMode()
        } else {
            setupSensorMode()
        }

        startGameLoop()
    }

    private fun setupButtonMode() {
        buttonLeft.visibility = View.VISIBLE
        buttonRight.visibility = View.VISIBLE

        buttonLeft.setOnClickListener {
            gameManager.moveRocketLeft()
            Log.d("MainActivity", "Rocket moved left. Position: ${gameManager.getRocketPosition()}")
            updateRocketPosition()
        }

        buttonRight.setOnClickListener {
            gameManager.moveRocketRight()
            Log.d("MainActivity", "Rocket moved right. Position: ${gameManager.getRocketPosition()}")
            updateRocketPosition()
        }
    }

    private fun setupSensorMode() {
        buttonLeft.visibility = View.GONE
        buttonRight.visibility = View.GONE

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    private fun startGameLoop() {
        gameRunnable = object : Runnable {
            override fun run() {
                if (!gameManager.isGameOver) {
                    createObstaclesAndCoins()
                    gameManager.updateObstacles()
                    refreshUI()
                    gameManager.increaseDistance(1)
                    handler.postDelayed(this, 500)
                } else {
                    handleGameOver()
                }
            }
        }
        handler.post(gameRunnable!!)
    }

    private fun updateRocketPosition() {
        val params = rocket.layoutParams as RelativeLayout.LayoutParams
        val laneWidth = mainLayout.width / 5
        val targetMargin = gameManager.getRocketPosition() * laneWidth

        Log.d("RocketMovement", "RocketPosition: ${gameManager.getRocketPosition()}, TargetMargin: $targetMargin")

        // Ensure left margin updates properly
        params.leftMargin = targetMargin
        rocket.layoutParams = params
    }

    private fun createObstaclesAndCoins() {
        if (gameManager.getObstacles().size >= 10) return

        val isCoin = Random.nextBoolean()
        val obstacleView = ImageView(this)
        obstacleView.setImageResource(if (isCoin) R.drawable.coin_icon else R.drawable.asteroid_icon)

        val lane = gameManager.getRandomLane()
        val params = RelativeLayout.LayoutParams(150, 150)

        val laneWidth = mainLayout.width / 5
        params.leftMargin = lane * laneWidth
        obstacleView.layoutParams = params
        obstacleView.y = 0f

        mainLayout.addView(obstacleView)

        val animation = ObjectAnimator.ofFloat(
            obstacleView, "translationY", 0f, mainLayout.height.toFloat()
        )
        animation.duration = (3000L - (gameManager.getDistance() / 10) * 50).coerceAtLeast(1500L)
        animation.start()

        animation.doOnEnd {
            mainLayout.removeView(obstacleView)
            if (gameManager.getRocketPosition() == lane) {
                if (isCoin) {
                    gameManager.increaseScore(100)
                    Toast.makeText(this@MainActivity, getString(R.string.collected_coin), Toast.LENGTH_SHORT).show()
                } else {
                    gameManager.decreaseLife()
                    Toast.makeText(this@MainActivity, getString(R.string.hit_obstacle), Toast.LENGTH_SHORT).show()
                    updateHeartIcons()
                }
            }
        }
    }

    private fun updateHeartIcons() {
        for (i in heartIcons.indices) {
            heartIcons[i].visibility = if (i < gameManager.getRemainingLives()) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun refreshUI() {
        distanceTextView.text = getString(R.string.distance_format, gameManager.getDistance())
        scoreTextView.text = getString(R.string.score_format, gameManager.getScore())
        updateHeartIcons()
    }

    private fun handleGameOver() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.game_over))
            .setMessage(getString(R.string.final_score, gameManager.getScore()))
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                finish()
            }
            .show()
        handler.removeCallbacksAndMessages(null)
    }

    private fun restartGame() {
        gameManager.resetGame()
        refreshUI()
        startGameLoop()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            if (x > 2) gameManager.moveRocketLeft()
            else if (x < -2) gameManager.moveRocketRight()
            updateRocketPosition()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(gameRunnable!!)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(gameRunnable!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useSensorMode) {
            sensorManager.unregisterListener(this)
        }
    }
}
