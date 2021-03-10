package com.veygard.android.geoquiz


data class Question(val index: Int, val text: String, val correctAnswer: String, val answer2: String, val answer3: String, val answer4: String, var questionShowed: Boolean)
