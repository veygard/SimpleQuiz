package com.veygard.android.geoquiz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class ScoreGameActivity : AppCompatActivity() {
    private lateinit var scoreBoardTextView: TextView
    private val quizViewModel: QuizViewModel by lazy { ViewModelProvider(this).get(QuizViewModel::class.java) }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_game)

        GameActivity.ThreadForTimerBar.interruptedForTimerBar = true //останавливаем поток таймера, почему-то нужно это повторно сделать здесь

        scoreBoardTextView = findViewById(R.id.score_board_textView)
        scoreBoardTextView.text = getString(R.string.text_result_score,
            intent.extras?.getString("scoreFinal")?.toInt(), quizViewModel.questionBank.size
        )
        quizViewModel.gameStarted = false
    }
    fun starOverButtonClickInScore(view: View) {
        quizViewModel.score = 0
        startActivity(Intent(this, MainActivity::class.java))
    }
}