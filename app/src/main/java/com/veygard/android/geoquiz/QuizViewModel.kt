package com.veygard.android.geoquiz

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    var currentIndex = 0
    var score = 0
    var questionBank : List<Question> = QuestionStorage.questionListForGame
    var numOfQuestionsForTextView = questionBank.size
    var numOfButtonWithCorrectAnswer = -1
    var answerAlreadyDone = false
    var gameStarted = false
    var correctAnswer: String = ""
    var hardModeStatus = false
    var timerModeStatus = false
    var timerModeMilliSeconds: Int = 999
}