import com.example.space_dash_amit_shlomo_01.data.HighScore
import com.google.android.gms.maps.model.LatLng

object HighScoresManager {
    private val highScores = mutableListOf(
        HighScore(playerName = "Player1", score = 1500, location = LatLng(37.7749, -122.4194)), // San Francisco
        HighScore(playerName = "Player2", score = 1200, location = LatLng(34.0522, -118.2437)), // Los Angeles
        HighScore(playerName = "Player3", score = 1000, location = LatLng(40.7128, -74.0060))  // New York
    )



    fun getTopScores(): List<HighScore> {
        return highScores.sortedByDescending { it.score }.take(10)
    }

    fun addPlayerScore(player: HighScore) {
        highScores.add(player)
        highScores.sortByDescending { it.score }
        if (highScores.size > 10) {
            highScores.removeAt(highScores.size - 1) // תמיכה ברמות API נמוכות
        }
    }

    fun isEligibleForHighScores(score: Int): Boolean {
        return highScores.size < 10 || score > highScores.minByOrNull { it.score }?.score ?: 0
    }

    fun sortHighScores() {
        highScores.sortByDescending { it.score }
    }
}
