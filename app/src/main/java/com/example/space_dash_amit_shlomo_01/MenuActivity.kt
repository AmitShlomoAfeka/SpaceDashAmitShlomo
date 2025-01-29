package com.example.space_dash_amit_shlomo_01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val startGameButton: Button = findViewById(R.id.menu_BTN_start)
        val highScoresButton: Button = findViewById(R.id.menu_BTN_highscores)
        val sensorModeRadio: RadioButton = findViewById(R.id.menu_RB_sensor_mode)
        val buttonModeRadio: RadioButton = findViewById(R.id.menu_RB_button_mode)

        startGameButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("USE_SENSOR_MODE", sensorModeRadio.isChecked)
            startActivity(intent)
        }

        highScoresButton.setOnClickListener {
            val intent = Intent(this, HighscoresActivity::class.java)
            startActivity(intent)
        }
    }
}
