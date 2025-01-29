package com.example.space_dash_amit_shlomo_01.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.space_dash_amit_shlomo_01.R
import com.example.space_dash_amit_shlomo_01.data.HighScore

class HighScoresAdapter(
    private val scores: List<HighScore>,
    private val onItemClicked: (HighScore) -> Unit
) : RecyclerView.Adapter<HighScoresAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName: TextView = itemView.findViewById(R.id.highscore_item_player_name)
        val score: TextView = itemView.findViewById(R.id.highscore_item_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.highscore_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val highScore = scores[position]
        holder.playerName.text = highScore.playerName
        holder.score.text = highScore.score.toString()

        holder.itemView.setOnClickListener {
            onItemClicked(highScore)
        }
    }

    override fun getItemCount(): Int = scores.size
}
