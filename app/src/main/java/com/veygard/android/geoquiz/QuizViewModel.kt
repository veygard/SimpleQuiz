package com.veygard.android.geoquiz

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel() : ViewModel() {
    var currentIndex = 0
    var score = 0
    var questionBank = Question.getQuestionList()
    var answerAlreadyDone = false
    var numOfQuestions = questionBank.size
    var gameStarted = false
}