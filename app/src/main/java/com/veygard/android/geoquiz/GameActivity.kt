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
    private lateinit var answerButton1: Button
    private lateinit var answerButton2: Button
    private lateinit var answerButton3: Button
    private lateinit var answerButton4: Button
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var questionNumTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var animClick: Animation //анимация кнопки нажатия
    private val quizViewModel: QuizViewModel by lazy { ViewModelProvider(this).get(QuizViewModel::class.java) } //для сохранения параметров при приостановке апп

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        questionTextView = findViewById(R.id.question_text_view)
        answerButton1 = findViewById(R.id.first_answer_button)
        answerButton1.setOnClickListener {
            checkAnswer(quizViewModel.currentIndex, answerButton1.text.toString())
            animClick = AnimationUtils.loadAnimation(this, R.anim.click_animation)
            answerButton1.startAnimation(animClick)
            answerButton1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
        }

        answerButton2 = findViewById(R.id.second_answer_button)
        answerButton2.setOnClickListener {
            checkAnswer(quizViewModel.currentIndex, answerButton2.text.toString())
            animClick = AnimationUtils.loadAnimation(this, R.anim.click_animation)
            answerButton2.startAnimation(animClick)
            answerButton2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
        }
        answerButton3 = findViewById(R.id.third_answer_button)
        answerButton3.setOnClickListener {
            checkAnswer(quizViewModel.currentIndex, answerButton3.text.toString())
            animClick = AnimationUtils.loadAnimation(this, R.anim.click_animation)
            answerButton3.startAnimation(animClick)
            answerButton3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
        }
        answerButton4 = findViewById(R.id.fourth_answer_button)
        answerButton4.setOnClickListener {
            checkAnswer(quizViewModel.currentIndex, answerButton4.text.toString())
            animClick = AnimationUtils.loadAnimation(this, R.anim.click_animation)
            answerButton4.startAnimation(animClick)
            answerButton4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_24, 0, 0, 0);
        }

        nextButton = findViewById(R.id.next_button)
        nextButton.setBackgroundResource(0)
        nextButton.setOnClickListener {
            nextQuestion()
            animClick = AnimationUtils.loadAnimation(this, R.anim.click_animation)
            nextButton.startAnimation(animClick)
        }

        updateQuestionAndAnswers() //показываем первый вопрос

        questionNumTextView = findViewById(R.id.questions_numbers)
        //выводим сколько вопросов осталось
        questionNumTextView.text = getQuestionNumText()
        //выводим результат
        scoreTextView = findViewById(R.id.score_board_textview)
        scoreTextView.text = getScoreText()
    }

    private fun updateQuestionAndAnswers() {
        val index = quizViewModel.currentIndex

        questionTextView.text = quizViewModel.questionBank[index].text

        //Сохраняем текст правильного ответа
        quizViewModel.correctAnswer = quizViewModel.questionBank[index].correctAnswer

        //массив ответов на вопрос
        val answersArray = arrayOf(
            quizViewModel.questionBank[index].correctAnswer,
            quizViewModel.questionBank[index].answer2,
            quizViewModel.questionBank[index].answer3,
            quizViewModel.questionBank[index].answer4
        )

       answersArray.shuffle()
        answerButton1.text = answersArray[0]
        answerButton2.text = answersArray[1]
        answerButton3.text = answersArray[2]
        answerButton4.text = answersArray[3]

        //отмечаем индекс правильной кнопки
        var count = 0
        answersArray.forEach { str ->
            if (str == quizViewModel.correctAnswer) {
                quizViewModel.numOfButtonWithCorrectAnswer = count
            }
            count++
        }
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
        quizViewModel.numOfQuestionsForTextView = questionsIndexNotShownList.size
        questionNumTextView.text = getQuestionNumText()
        scoreTextView.text = getScoreText()
        //получаем случайный индекс следующего вопроса
        quizViewModel.currentIndex =
            questionsIndexNotShownList[(Math.random() * questionsIndexNotShownList.size).toInt()]
        updateQuestionAndAnswers()
        //отмечаем что вопрос уже показывался
        quizViewModel.questionBank[quizViewModel.currentIndex].questionShowed = true

        //откатываем проверку на повторное нажатие и меняём цвет кнопок на дефолтные
        quizViewModel.answerAlreadyDone = false
        val colorPrimary = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
        answerButton1.setBackgroundColor(colorPrimary)
        answerButton2.setBackgroundColor(colorPrimary)
        answerButton3.setBackgroundColor(colorPrimary)
        answerButton4.setBackgroundColor(colorPrimary)
        //анимация поворота нового вопроса
        animClick = AnimationUtils.loadAnimation(this, R.anim.anim_translate)
        questionTextView.startAnimation(animClick)

        removeDrawableFromButtons()
    }

    private fun removeDrawableFromButtons(){
        answerButton1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        answerButton2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        answerButton3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        answerButton4.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }


    private fun checkAnswer(index: Int, answer: String) {
        //подготавливаем ответ для тоста
        val correctAnswer = quizViewModel.correctAnswer

        val checkAnswerStr: String
        if (answer == correctAnswer && !quizViewModel.answerAlreadyDone) {
            quizViewModel.score++
            checkAnswerStr = getString(R.string.correct_toast)
            //обновляем поле результата
            scoreTextView.text = getScoreText()
        } else if (!quizViewModel.answerAlreadyDone) {
            checkAnswerStr = getString(R.string.incorrect_toast)
        } else {
            checkAnswerStr = getString(R.string.already_answered_toast)
        }

        //меняем цвет кнопок
        changeButtonColorsAfterAnswer()

        val toastMessage = Toast.makeText(
            applicationContext,
            checkAnswerStr,
            Toast.LENGTH_SHORT
        )
        toastMessage.setGravity(Gravity.TOP, 0, 200)
        toastMessage.show()
        quizViewModel.answerAlreadyDone = true
    }

    //у правильного ответа ставим цвет зеленый, остальные серые
    private fun changeButtonColorsAfterAnswer() {
        val colorCorrectAnswer = ContextCompat.getColor(applicationContext, R.color.correct_answer)
        val colorIncorrectAnswer =
            ContextCompat.getColor(applicationContext, R.color.incorrect_answer)

        answerButton1.setBackgroundColor(colorIncorrectAnswer)
        answerButton2.setBackgroundColor(colorIncorrectAnswer)
        answerButton3.setBackgroundColor(colorIncorrectAnswer)
        answerButton4.setBackgroundColor(colorIncorrectAnswer)

        when (quizViewModel.numOfButtonWithCorrectAnswer) {
            0 -> answerButton1.setBackgroundColor(colorCorrectAnswer)
            1 -> answerButton2.setBackgroundColor(colorCorrectAnswer)
            2 -> answerButton3.setBackgroundColor(colorCorrectAnswer)
            3 -> answerButton4.setBackgroundColor(colorCorrectAnswer)
        }
    }

    //методы получения текста для отображения кол-ва вопросов и текущий результат
    private fun getQuestionNumText(): String = getString(
        R.string.text_num_of_questions,
        quizViewModel.numOfQuestionsForTextView,
        quizViewModel.questionBank.size
    )


    private fun getScoreText(): String = getString(
        R.string.text_score,
        quizViewModel.score
    )


    fun startOver() {
        quizViewModel.score = 0
        quizViewModel.gameStarted = false
        startActivity(Intent(this, MainActivity::class.java))
    }

    //При нажатии кнопки заново или нажатии кнопки назад - вызываем диалог
    fun starOverButtonClick(view: View) {
        val dialog = BackPressedDialogFragment()
        val manager = supportFragmentManager
        dialog.show(manager, "")
    }

    override fun onBackPressed() {
        val dialog = BackPressedDialogFragment()
        val manager = supportFragmentManager
        dialog.show(manager, "")
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
