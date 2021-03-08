package com.veygard.android.geoquiz

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    var currentIndex = 0
    var score = 0
    var questionBank : List<Question> = Question.questionListForGame
    var answerAlreadyDone = false
    var numOfQuestionsForTextView = questionBank.size
    var gameStarted = false
    var correctAnswer: String = ""
    var numOfButtonWithCorrectAnswer = -1
    var hardModeStatus = false
    var timerModeStatus = false
    var timerModeSeconds: Int = 999
}