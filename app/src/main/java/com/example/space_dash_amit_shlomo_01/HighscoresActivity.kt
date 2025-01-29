package com.example.space_dash_amit_shlomo_01

import HighScoresFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class HighscoresActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscores)

        // טעינת Fragment של השיאים
        supportFragmentManager.beginTransaction()
            .replace(R.id.highscores_fragment_container, HighScoresFragment())
            .commit()
    }
    fun loadFragment(fragment: androidx.fragment.app.Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.highscores_fragment_container, fragment, tag)
            .commit()
    }

}
