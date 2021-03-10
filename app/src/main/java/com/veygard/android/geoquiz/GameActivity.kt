package com.veygard.android.geoquiz


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.veygard.android.geoquiz.ThreadForTimerBar.Companion.interruptedForTimerBar
import java.lang.reflect.Type
import java.text.DecimalFormat


private const val TAG = "GameActivity"
private const val CURRENT_INDEX = "currentIndex"
private const val SCORE = "score"
private const val ANSWER_ALREADY_DONE = "answerAlreadyDone"
private const val NUM_OF_QUESTIONS_FOR_TEXT_VIEW = "numOfQuestionsForTextView"
private const val GAME_STARTED = "gameStarted"
private const val CORRECT_ANSWER = "correctAnswer"
private const val NUM_OF_BUTTON_CORRECT_ANSWER = "numOfButtonWithCorrectAnswer"
private const val HARD_MODE_STATUS = "hardModeStatus"
private const val TIMER_MODE_STATUS = "timerModeStatus"
private const val TIMER_MODE_SECONDS = "timerModeSeconds"
private const val QUESTION_BANK = "questionBank "

class GameActivity : AppCompatActivity() {
    private lateinit var answerButton1: Button
    private lateinit var answerButton2: Button
    private lateinit var answerButton3: Button
    private lateinit var answerButton4: Button
    private lateinit var showAnswersButton: Button
    private lateinit var startOver: Button
    private lateinit var floatingButtonForStartOver: FloatingActionButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var questionNumTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var animClick: Animation //анимация кнопки нажатия
    private lateinit var timerProgressBar: ProgressBar
    private lateinit var timerTextView: TextView
    private var numOfQuestionsAtStart = 0
    private var restartCheck = false


    val quizViewModel: QuizViewModel by lazy { ViewModelProvider(this).get(QuizViewModel::class.java) } //для сохранения параметров при приостановке апп

    companion object {
        const val TIMER_MILLISECONDS_LEFT = "timerMillisecondsLeft"
        const val TIMER_MILLISECONDS_MAX = "timerMillisecondsMax"
        lateinit var saveGameActStatusFile: SharedPreferences
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //инициализируем файл сохранения статуса
        saveGameActStatusFile = getSharedPreferences("Game_activity_savings", MODE_PRIVATE)
        //проверяем это новая игра или прерванный статус

        questionTextView = findViewById(R.id.question_text_view)
        timerProgressBar = findViewById(R.id.timer_progressBar)
        timerTextView = findViewById(R.id.timer_countdown_textView)

        restartCheck = intent.extras?.getBoolean("restartCheck") ?: true
        //получаем аргументы из прошлой активности, или из файла по статусу рестарта
        getGameArguments(restartCheck)


        buttonsAndListeners() //кнопки и слушатели

        updateQuestionAndAnswers() //показываем первый вопрос

        questionNumTextView = findViewById(R.id.questions_numbers)
        //выводим сколько вопросов осталось
        questionNumTextView.text = getQuestionNumText()
        //выводим результат
        scoreTextView = findViewById(R.id.score_board_textView)
        scoreTextView.text = getScoreText()

    }

    private fun buttonsAndListeners(){
        answerButton1 = findViewById(R.id.first_answer_button)
        answerButton1.setOnClickListener {
            answerButtonListenerAction(answerButton1)
        }
        answerButton2 = findViewById(R.id.start_game_button)
        answerButton2.setOnClickListener {
            answerButtonListenerAction(answerButton2)
        }
        answerButton3 = findViewById(R.id.third_answer_button)
        answerButton3.setOnClickListener {
            answerButtonListenerAction(answerButton3)
        }
        answerButton4 = findViewById(R.id.fourth_answer_button)
        answerButton4.setOnClickListener {
            answerButtonListenerAction(answerButton4)
        }
        nextButton = findViewById(R.id.next_button)
        nextButton.setBackgroundResource(0)
        nextButton.setOnClickListener {
            nextQuestion()
            animClick = AnimationUtils.loadAnimation(this, R.anim.click_animation)
            nextButton.startAnimation(animClick)
        }
        showAnswersButton = findViewById(R.id.show_answers_button)
        startOver = findViewById(R.id.start_over)
        var startOverButtonVisible = false
        floatingButtonForStartOver = findViewById(R.id.floating_button_start_over)
        floatingButtonForStartOver.setOnClickListener {
            if (!startOverButtonVisible) {
                startOver.visibility = View.VISIBLE
                startOverButtonVisible = true
            } else {
                startOver.visibility = View.INVISIBLE
                startOverButtonVisible = false
            }
        }
    }
    private fun getGameArguments(restartCheck: Boolean){
        if (!restartCheck) {
            //получаем из майн кол-во вопросов для отображения
            numOfQuestionsAtStart = intent.extras?.getInt("numOfQuestionsAtStart") ?: 20
            //формируем список вопросов
            formQuestionList(numOfQuestionsAtStart)
            //чекаем статус сложной игры
            quizViewModel.hardModeStatus = intent.extras?.getBoolean("hardModeStatus") ?: false
            //чекаем статус таймера
            quizViewModel.timerModeStatus = intent.extras?.getBoolean("timerModeStatus") ?: false
            quizViewModel.timerModeMilliSeconds = intent.extras?.getInt("timerModeMilliSeconds") ?: 60
        } else {
            getGameSettingsFromFile()
            loadQuestionList()
        }
    }

    private fun showTimerProgressBar() {
        if (quizViewModel.timerModeStatus) {
            timerProgressBar.visibility = View.VISIBLE
            timerTextView.visibility = View.VISIBLE
        }
    }
    private  fun settingTimerBarArguments(restartCheck:Boolean){
        if (!restartCheck){
            timerProgressBar.max = quizViewModel.timerModeMilliSeconds
            timerProgressBar.progress = quizViewModel.timerModeMilliSeconds
            val doubleNum = (quizViewModel.timerModeMilliSeconds).toDouble() / 10
            val formatNum = DecimalFormat("0.#")
            timerTextView.text = formatNum.format(doubleNum).toString()
            showTimerProgressBar()
        }
        else {
            //восстанавливаем положение таймера
            val millisecondsMax = saveGameActStatusFile.getInt(TIMER_MILLISECONDS_MAX, 60)
            timerProgressBar.max = millisecondsMax
            val millisecondsLeft = saveGameActStatusFile.getInt(TIMER_MILLISECONDS_LEFT, 60)
            timerProgressBar.progress = millisecondsLeft
            val doubleNum = (millisecondsLeft).toDouble() / 10
            val formatNum = DecimalFormat("0.#")
            timerTextView.text = formatNum.format(doubleNum).toString()
            showTimerProgressBar()
        }
    }


    //метод для слушателя, который проверяет ответ, включает анимацию нажатия, и рисует галочку на кнопке
    private fun answerButtonListenerAction(button: Button) {
        if (!quizViewModel.answerAlreadyDone) {
            button.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_baseline_check_24,
                0,
                0,
                0
            )
        }
        checkAnswer(button.text.toString())
        animClick = AnimationUtils.loadAnimation(this, R.anim.click_animation)
        button.startAnimation(animClick)
    }

    private fun checkAnswer(answer: String) {
        interruptedForTimerBar = true
        val correctAnswer = quizViewModel.correctAnswer
        if (answer == correctAnswer && !quizViewModel.answerAlreadyDone) {
            quizViewModel.score++
            //обновляем поле результата
            scoreTextView.text = getScoreText()
            //если ответ ложный и игра в сложном режиме - переходим к результатам
        } else if (answer != correctAnswer && quizViewModel.hardModeStatus) {
            val toastMessage = Toast.makeText(
                applicationContext,
                R.string.incorrect_toast,
                Toast.LENGTH_LONG
            )
            toastMessage.setGravity(Gravity.TOP, 0, 200)
            toastMessage.show()
            Handler(Looper.getMainLooper()).postDelayed({
                gameOver()
            }, 1000)
        }
        //меняем цвет кнопок
        changeButtonColorsAfterAnswer()
        //отметка что уже отвечали на вопрос
        quizViewModel.answerAlreadyDone = true
    }

    fun nextQuestion() {
        interruptedForTimerBar = false
        restartCheck = false
        //если пытаются нажать следующий вопрос до ответа.
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
        //проверяем сколько вопросов еще не было показано, делаем список
        val questionsIndexNotShownList = mutableListOf<Int>()
        for ((index, question) in quizViewModel.questionBank.withIndex()) {
            if (!question.questionShowed) {
                questionsIndexNotShownList.add(index)
            }
        }
        //если все вопросы показаны переходим на активность итогов
        if (questionsIndexNotShownList.size == 0) {
            gameOver()
            return
        }
        quizViewModel.numOfQuestionsForTextView = questionsIndexNotShownList.size
        questionNumTextView.text = getQuestionNumText()
        scoreTextView.text = getScoreText()
        //получаем случайный индекс следующего вопроса
        if (!restartCheck) {
            quizViewModel.currentIndex =
                questionsIndexNotShownList[(Math.random() * questionsIndexNotShownList.size).toInt()]
            updateQuestionAndAnswers()
        } else {
            quizViewModel.currentIndex = saveGameActStatusFile.getInt(CURRENT_INDEX, 0)
        }

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

    //меняем текст у поля вопроса и кнопки ответов в соответствии с новым индексом
    private fun updateQuestionAndAnswers() {

        hideAnswersButtonsCheck()

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
        //тусуем индексы ответов в массиве и присваиваем кнопкам текст ответа
        answersArray.shuffle()
        answerButton1.text = answersArray[0]
        answerButton2.text = answersArray[1]
        answerButton3.text = answersArray[2]
        answerButton4.text = answersArray[3]

        //отмечаем индекс правильной кнопки(для дальнейшей проверки)
        var count = 0
        answersArray.forEach { str ->
            if (str == quizViewModel.correctAnswer) {
                quizViewModel.numOfButtonWithCorrectAnswer = count
            }
            count++
        }
        //отмечаем что вопрос уже показывался
        quizViewModel.questionBank[quizViewModel.currentIndex].questionShowed = true
    }

    private fun removeDrawableFromButtons() {
        answerButton1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        answerButton2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        answerButton3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        answerButton4.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    fun gameOver() {
        val intent = Intent(this, ScoreGameActivity::class.java)
        //передаём сумму правильных ответов
        intent.putExtra("scoreFinal", quizViewModel.score.toString())
        intent.putExtra("numOfQuestions", quizViewModel.questionBank.size.toString())
        intent.putExtra("restartCheck", false)
        startActivity(intent)
    }

    private fun hideAnswersButtonsCheck() {
        if (quizViewModel.timerModeStatus) {
            settingTimerBarArguments(restartCheck)
            showAnswersButton.visibility = View.VISIBLE
            answerButton1.visibility = View.INVISIBLE
            answerButton2.visibility = View.INVISIBLE
            answerButton3.visibility = View.INVISIBLE
            answerButton4.visibility = View.INVISIBLE
        }
    }

    //у правильного ответа ставим зеленый цвет, остальные серые
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

    //завершаем игру
    fun startOver() {
        quizViewModel.score = 0
        quizViewModel.gameStarted = false
        val editor = MainActivity.saveMainActSettingsFile.edit()
        editor.putString(MainActivity.ACTIVITY_NAME, "main_activity").apply()
        startActivity(Intent(this, MainActivity::class.java))
    }

    //метод старта прогресс бара отсчёта
    private fun startTimerProgressBar(secondsLeft:Int, secondsMax:Int) {
        if (quizViewModel.timerModeStatus) {
            interruptedForTimerBar = false
            val thread = Thread(
                ThreadForTimerBar(
                    timerProgressBar,
                    timerTextView,
                    secondsLeft,
                    secondsMax,
                    this
                )
            )
            thread.start()
        }
    }

    fun showAnswersButtonClick(view: View) {
        answerButton1.visibility = View.VISIBLE
        answerButton2.visibility = View.VISIBLE
        answerButton3.visibility = View.VISIBLE
        answerButton4.visibility = View.VISIBLE
        showAnswersButton.visibility = View.INVISIBLE
        val secondsLeftNow = timerProgressBar.progress
        val secondsMaxNow = timerProgressBar.max
        startTimerProgressBar(secondsLeftNow, secondsMaxNow)
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
        val editor = MainActivity.saveMainActSettingsFile.edit()
        editor.putString(MainActivity.ACTIVITY_NAME, "game_activity").apply()
        setGameSettingsToFile()
        saveQuestionList()
    }

    private fun setGameSettingsToFile() {
        val gameEditor = saveGameActStatusFile.edit()
        gameEditor.putInt(CURRENT_INDEX, quizViewModel.currentIndex).apply()
        gameEditor.putInt(SCORE, quizViewModel.score).apply()
        gameEditor.putInt(NUM_OF_QUESTIONS_FOR_TEXT_VIEW, quizViewModel.numOfQuestionsForTextView)
            .apply()
        gameEditor.putInt(NUM_OF_BUTTON_CORRECT_ANSWER, quizViewModel.numOfButtonWithCorrectAnswer)
            .apply()
        gameEditor.putInt(TIMER_MODE_SECONDS, quizViewModel.timerModeMilliSeconds).apply()
        gameEditor.putBoolean(ANSWER_ALREADY_DONE, quizViewModel.answerAlreadyDone).apply()
        gameEditor.putBoolean(GAME_STARTED, quizViewModel.gameStarted).apply()
        gameEditor.putBoolean(HARD_MODE_STATUS, quizViewModel.hardModeStatus).apply()
        gameEditor.putBoolean(TIMER_MODE_STATUS, quizViewModel.timerModeStatus).apply()
        gameEditor.putString(CORRECT_ANSWER, quizViewModel.correctAnswer).apply()
    }

    private fun getGameSettingsFromFile() {
        quizViewModel.currentIndex = saveGameActStatusFile.getInt(CURRENT_INDEX, 0)
        quizViewModel.score = saveGameActStatusFile.getInt(SCORE, 0)
        quizViewModel.numOfQuestionsForTextView = saveGameActStatusFile.getInt(
            NUM_OF_QUESTIONS_FOR_TEXT_VIEW, 0
        )
        quizViewModel.numOfButtonWithCorrectAnswer = saveGameActStatusFile.getInt(
            NUM_OF_BUTTON_CORRECT_ANSWER, 0
        )
        quizViewModel.answerAlreadyDone =
            saveGameActStatusFile.getBoolean(ANSWER_ALREADY_DONE, false)
        quizViewModel.gameStarted = saveGameActStatusFile.getBoolean(GAME_STARTED, false)
        quizViewModel.correctAnswer = saveGameActStatusFile.getString(CORRECT_ANSWER, "").toString()
        quizViewModel.hardModeStatus = saveGameActStatusFile.getBoolean(HARD_MODE_STATUS, false)
        quizViewModel.timerModeStatus = saveGameActStatusFile.getBoolean(TIMER_MODE_STATUS, false)
        quizViewModel.timerModeMilliSeconds = saveGameActStatusFile.getInt(TIMER_MODE_SECONDS, 60)
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun saveQuestionList() {
        val editor = saveGameActStatusFile.edit()
        val gson = Gson()
        val json = gson.toJson(quizViewModel.questionBank)
        editor.putString(QUESTION_BANK, json).apply()
    }

    private fun loadQuestionList() {
        val gson = Gson()
        val json = saveGameActStatusFile.getString(QUESTION_BANK, null)
        val type: Type = object : TypeToken<ArrayList<Question>>() {}.type
        quizViewModel.questionBank = gson.fromJson(json, type)
        if (quizViewModel.questionBank.isEmpty()) {
            quizViewModel.questionBank = listOf()
        }
    }

    private fun formQuestionList(countQ: Int) {
        val questionList = QuestionStorage.getQuestionListAtStart()
        //получаем индексы для выгрузки радномного списка вопросов
        val indexStart = (Math.random() * (questionList.size - countQ)).toInt()
        val indexEnd = indexStart + countQ
        //формируем итоговый список вопросов для новой игры
        QuestionStorage.questionListForGame = questionList.subList(indexStart, indexEnd)
    }
}

