package com.veygard.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class AfterStart : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var returnButton: ImageButton
    private lateinit var questionTextView: TextView
    private var currentIndex = 0
    private var indexSaver = 0
    private lateinit var questionBank: List<Question>
    private var answeredQuestion = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_start)

        val question = Question("1", true, false)
        questionBank = question.getQuestionList()




        trueButton = findViewById(R.id.true_button)
        trueButton.setOnClickListener {
//            val toastMessage = Toast.makeText(
//                applicationContext,
//                checkAnswer(currentIndex, true),
//                Toast.LENGTH_SHORT
//            )
//            toastMessage.setGravity(Gravity.CENTER_VERTICAL, 0, -200)
//            toastMessage.show()
            checkAnswer(currentIndex, true)
            answeredQuestion = true
        }
        falseButton = findViewById(R.id.false_button)
        falseButton.setOnClickListener {
//            val toastMessage = Toast.makeText(
//                applicationContext,
//                checkAnswer(currentIndex, false),
//                Toast.LENGTH_SHORT
//            )
//            toastMessage.setGravity(Gravity.CENTER_VERTICAL, 0, -200)
//            toastMessage.show()
            checkAnswer(currentIndex, false)
            answeredQuestion = true
        }

        questionTextView = findViewById(R.id.question_text_view)

        nextButton = findViewById(R.id.next_button)
        nextButton.setBackgroundResource(0)
        nextButton.setOnClickListener {
            nextQuestion()
        }
        questionTextView.setOnClickListener {
            nextQuestion()
        }

        returnButton = findViewById(R.id.return_button)
        returnButton.setBackgroundResource(0)
        returnButton.setOnClickListener {
            currentIndex = indexSaver
            updateQuestion()
        }
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
        if (questionsIndexNotShownList.size == 0) {
            endGame()
        }

        //получаем индекс следующего вопроса
        currentIndex = questionsIndexNotShownList[(Math.random() * questionsIndexNotShownList.size).toInt()]
        updateQuestion()
        //отмечаем что вопрос уже показывался
        questionBank[currentIndex].questionShowed = true
        answeredQuestion = false
    }

    private fun endGame() {
        val toastMessage = Toast.makeText(
            applicationContext,
            "Игра завершена",
            Toast.LENGTH_SHORT
        )
        toastMessage.setGravity(Gravity.CENTER_VERTICAL, 0, -200)
        toastMessage.show()
    }


    private fun checkAnswer(index: Int, check: Boolean) {
        val checkAnswerStr = if (questionBank[index].answer == check && !answeredQuestion) {
            getString(R.string.correct_toast)
        } else if (!answeredQuestion) {
            getString(R.string.incorrect_toast)
        } else {
            getString(R.string.already_answered_toast)
        }

        val toastMessage = Toast.makeText(
            applicationContext,
            checkAnswerStr,
            Toast.LENGTH_SHORT
        )
        toastMessage.setGravity(Gravity.CENTER_VERTICAL, 0, -200)
        toastMessage.show()
    }
}
