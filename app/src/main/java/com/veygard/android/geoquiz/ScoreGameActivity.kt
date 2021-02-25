package com.veygard.android.geoquiz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class ScoreGameActivity : AppCompatActivity() {
    private lateinit var scoreBoardTextView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_game)
        scoreBoardTextView = findViewById(R.id.score_board_textview)

        scoreBoardTextView.text = "${getString(R.string.text_result_score)} ${intent.extras?.getString("scoreFinal")} ${getString(R.string.text_result_score_ending)}"
    }
    fun starOverButtonClickInScore(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}