package com.veygard.android.geoquiz


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider


private const val TAG = "GameActivity"

class GameActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var questionNumTextView: TextView
    private lateinit var scoreTextView: TextView

    private lateinit var animClick: Animation //анимация кнопки нажатия

    private val quizViewModel: QuizViewModel by lazy { ViewModelProvider(this).get(QuizViewModel::class.java) } //для сохранения параметров при приостановке апп

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_fragment)

        questionTextView = findViewById(R.id.question_text_view)
        trueButton = findViewById(R.id.true_button)
        trueButton.setOnClickListener {
            checkAnswer(quizViewModel.currentIndex, true)
            animClick = AnimationUtils.loadAnimation(this,R.anim.click_animation)
            trueButton.startAnimation(animClick)
        }

        falseButton = findViewById(R.id.false_button)
        falseButton.setOnClickListener {
            checkAnswer(quizViewModel.currentIndex, false)
            animClick = AnimationUtils.loadAnimation(this,R.anim.click_animation)
            falseButton.startAnimation(animClick)
        }
        nextButton = findViewById(R.id.next_button)
        nextButton.setBackgroundResource(0)
        nextButton.setOnClickListener {
            nextQuestion()
            animClick = AnimationUtils.loadAnimation(this,R.anim.click_animation)
            nextButton.startAnimation(animClick)
        }

        updateQuestion() //показываем первый вопрос

        questionNumTextView = findViewById(R.id.questions_numbers)
        //выводим сколько вопросов осталось
        questionNumTextView.text = getString(
            R.string.text_num_of_questions,
            quizViewModel.numOfQuestions,
            quizViewModel.questionBank.size
        )
        //выводим результат
        scoreTextView = findViewById(R.id.score_board_textview)
        scoreTextView.text = getString(
            R.string.text_result_score,
            quizViewModel.score
        )
    }



    private fun updateQuestion() {
        questionTextView.text = quizViewModel.questionBank[quizViewModel.currentIndex].text
    }


    private fun nextQuestion() {
        //проверяем дан ли ответ
        if (!quizViewModel.answerAlreadyDone) {
            val toastMessage = Toast.makeText(
                applicationContext,
                R.string.first_need_answer,
                Toast.LENGTH_SHORT
            )
            toastMessage.setGravity(Gravity.TOP, 0, 200)
            toastMessage.show()
            return
        }
        //проверяем сколько вопросов еще не показано
        val questionsIndexNotShownList = mutableListOf<Int>()
        for ((index, question) in quizViewModel.questionBank.withIndex()) {
            if (!question.questionShowed) {
                questionsIndexNotShownList.add(index)
            }
        }
        //если все вопросы показаны переходим на активность итогов
        if (questionsIndexNotShownList.size == 0) {
            val intent = Intent(this, ScoreGameActivity::class.java)
            intent.putExtra("scoreFinal", quizViewModel.score.toString())
            startActivity(intent)
            return
        }
        quizViewModel.numOfQuestions = questionsIndexNotShownList.size
        questionNumTextView.text = getString(
            R.string.text_num_of_questions,
            quizViewModel.numOfQuestions,
            quizViewModel.questionBank.size
        )
        scoreTextView.text = getString(
            R.string.text_result_score,
            quizViewModel.score
        )

        //получаем случайный индекс следующего вопроса
        quizViewModel.currentIndex =
            questionsIndexNotShownList[(Math.random() * questionsIndexNotShownList.size).toInt()]
        updateQuestion()

        //отмечаем что вопрос уже показывался
        quizViewModel.questionBank[quizViewModel.currentIndex].questionShowed = true
        quizViewModel.answerAlreadyDone = false
        val colorPrimary = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
        trueButton.setBackgroundColor(colorPrimary)
        falseButton.setBackgroundColor(colorPrimary)
    }




    private fun checkAnswer(index: Int, check: Boolean) {
        //подготавливаем ответ для тоста

        val checkAnswerStr: String
            if (quizViewModel.questionBank[index].answer == check && !quizViewModel.answerAlreadyDone) {
                quizViewModel.score++
                checkAnswerStr =  getString(R.string.correct_toast)
                //обновляем результат
                scoreTextView.text = getString(
                    R.string.text_result_score,
                    quizViewModel.score
                )
            } else if (!quizViewModel.answerAlreadyDone) {
                checkAnswerStr = getString(R.string.incorrect_toast)
            } else {
                checkAnswerStr = getString(R.string.already_answered_toast)
            }

        //меняем цвет кнопок, указываем на правильный ответ
        val colorCorrectAnswer = ContextCompat.getColor(applicationContext, R.color.correct_answer)
        val colorIncorrectAnswer = ContextCompat.getColor(
            applicationContext,
            R.color.incorrect_answer
        )
        when (quizViewModel.questionBank[index].answer) {
            true -> {
                trueButton.setBackgroundColor(colorCorrectAnswer)
                falseButton.setBackgroundColor(colorIncorrectAnswer)
            }
            false -> {
                trueButton.setBackgroundColor(colorIncorrectAnswer)
                falseButton.setBackgroundColor(colorCorrectAnswer)
            }
        }

        val toastMessage = Toast.makeText(
            applicationContext,
            checkAnswerStr,
            Toast.LENGTH_SHORT
        )
        toastMessage.setGravity(Gravity.TOP, 0, 200)
        toastMessage.show()
        quizViewModel.answerAlreadyDone = true
    }



    fun starOverButtonClick(view: View) {
        quizViewModel.score = 0
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}
