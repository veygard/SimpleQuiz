package com.veygard.android.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class GameActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var returnButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var questionAnswerView: TextView
    private lateinit var questionBank: List<Question>
    private var currentIndex = 0
    private var indexSaver = 0
    private var answerAlreadyDone = false
    private var score = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_fragment)

        val question = Question("1", answer = true, questionShowed = false) //пустышка для вызова метода
        questionBank = question.getQuestionList()

        trueButton = findViewById(R.id.true_button)
        trueButton.setOnClickListener {
            checkAnswer(currentIndex, true)
            answerAlreadyDone = true
        }
        falseButton = findViewById(R.id.false_button)
        falseButton.setOnClickListener {
            checkAnswer(currentIndex, false)
            answerAlreadyDone = true
        }

        questionTextView = findViewById(R.id.question_text_view)
        questionAnswerView = findViewById(R.id.question_answer_view)

        nextButton = findViewById(R.id.next_button)
        nextButton.setBackgroundResource(0)
        nextButton.setOnClickListener {
            nextQuestion()
        }


//        returnButton = findViewById(R.id.return_button)
//        returnButton.setBackgroundResource(0)
//        returnButton.setOnClickListener {
//            currentIndex = indexSaver
//            updateQuestion()
//        }
        updateQuestion()
    }

    private fun updateQuestion() {
        questionTextView.text = questionBank[currentIndex].text
    }


    private fun nextQuestion() {
        indexSaver = currentIndex
        //проверяем сколько вопросов еще не показано
        val questionsIndexNotShownList = mutableListOf<Int>()
        for ((index, question) in questionBank.withIndex()) {
            if (!question.questionShowed) {
                questionsIndexNotShownList.add(index)
            }
        }
        //если все вопросы показаны - заканчиваем игру
        if (questionsIndexNotShownList.size == 0) {
            val intent = Intent(this, ScoreGameActivity::class.java)
            intent.putExtra("scoreFinal", score.toString())
            startActivity(intent)
            return
        }
        //получаем индекс следующего вопроса
        questionAnswerView.text = ""
        currentIndex = questionsIndexNotShownList[(Math.random() * questionsIndexNotShownList.size).toInt()]
        updateQuestion()
        //отмечаем что вопрос уже показывался
        questionBank[currentIndex].questionShowed = true
        answerAlreadyDone = false
    }


    private fun checkAnswer(index: Int, check: Boolean) {
        val checkAnswerStr = if (questionBank[index].answer == check && !answerAlreadyDone) {
            score++
            getString(R.string.correct_toast)
        } else if (!answerAlreadyDone) {
            getString(R.string.incorrect_toast)
        } else {
            getString(R.string.already_answered_toast)
        }

        when(questionBank[index].answer){
            true -> questionAnswerView.text = getText(R.string.show_answer_correct)
            false -> questionAnswerView.text = getText(R.string.show_answer_incorrect)
        }

        val toastMessage = Toast.makeText(
            applicationContext,
            checkAnswerStr,
            Toast.LENGTH_SHORT
        )
        toastMessage.setGravity(Gravity.CENTER_VERTICAL, 0, -200)
        toastMessage.show()
    }

    fun starOverButtonClick(view: View) {
        score = 0
        startActivity(Intent(this, MainActivity::class.java))
    }
}
