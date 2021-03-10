package com.veygard.android.geoquiz

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.veygard.android.geoquiz.ThreadForTimerBar.Companion.interruptedForTimerBar

class ScoreGameActivity : AppCompatActivity() {
    private lateinit var scoreBoardTextView: TextView
    private val quizViewModel: QuizViewModel by lazy { ViewModelProvider(this).get(QuizViewModel::class.java) }
    private lateinit var saveScoreActStatusFile: SharedPreferences
    private var score = 0
    private var numOfQuestions = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_game)

        saveScoreActStatusFile = getSharedPreferences("Score_activity_savings", MODE_PRIVATE)

        val restartCheck = intent.extras?.getBoolean("restartCheck") ?: true

        if(restartCheck){
            loadInfo()
        }
        else{
            score = intent.extras?.getString("scoreFinal")?.toInt() ?: 0
            numOfQuestions = intent.extras?.getString("numOfQuestions")?.toInt() ?: 0
        }

        interruptedForTimerBar = true //останавливаем поток таймера, почему-то нужно это повторно сделать здесь
        scoreBoardTextView = findViewById(R.id.score_board_textView)
        scoreBoardTextView.text = getString(R.string.text_result_score,
            score, numOfQuestions
        )
        quizViewModel.gameStarted = false
    }
    fun starOverButtonClickInScore(view: View) {
        quizViewModel.score = 0
        val editor = MainActivity.saveMainActSettingsFile.edit()
        editor.putString(MainActivity.ACTIVITY_NAME, "main_activity").apply()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onStop() {
        super.onStop()
        saveInfo()
    }
    private fun saveInfo(){
        val editor = MainActivity.saveMainActSettingsFile.edit()
        editor.putString(MainActivity.ACTIVITY_NAME, "score_activity").apply()
        val editorHere = saveScoreActStatusFile.edit()
        editorHere.putInt("score", score)
        editorHere.putInt("numOfQuestions", numOfQuestions)
        editorHere.apply()
    }
    private fun loadInfo(){
        score = saveScoreActStatusFile.getInt("score", 0)
        numOfQuestions = saveScoreActStatusFile.getInt("numOfQuestions", 0)
    }
}