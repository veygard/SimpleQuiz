package com.veygard.android.geoquiz

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat


private const val TAG = "MainActivity"
private const val NUM_OF_QUESTIONS = "numOfQuestionsAtStart"
private const val TIMER_MODE_SECONDS = "timerModeSeconds"
private const val HARD_MODE_STATUS = "hardModeStatus"
private const val TIMER_MODE_STATUS = "timerModeStatus"


class MainActivity : AppCompatActivity() {
    private lateinit var spinnerQuestionNum: Spinner
    private lateinit var spinnerTimerMode: Spinner
    private var numOfQuestionsAtStart = 20
    private var timerModeSeconds = 60
    private lateinit var switchButtonHardMode: SwitchCompat
    private lateinit var switchButtonTimerMode: SwitchCompat
    private var hardModeStatus = false
    private var timerModeStatus = false


    companion object {
        const val ACTIVITY_NAME = "activityName"
        lateinit var saveMainActSettingsFile: SharedPreferences
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_main)

        spinnerQuestionNum = findViewById(R.id.spinner_num_questions)
        spinnerQuestionNum.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(R.array.NumQuestionsSpinner)
                numOfQuestionsAtStart = choose[selectedItemPosition].toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        //реализация свитчера сложного режима
        switchButtonHardMode = findViewById(R.id.hard_mode_switch)
        switchButtonHardMode.setOnCheckedChangeListener { buttonView, isChecked ->
            hardModeStatus = isChecked
        }


        spinnerTimerMode = findViewById(R.id.spinner_timer_mode)
        spinnerTimerMode.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(R.array.TimerModeSpinnerArray)
                timerModeSeconds = choose[selectedItemPosition].toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        switchButtonTimerMode = findViewById(R.id.timer_mode_switch)
        switchButtonTimerMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                timerModeStatus = isChecked
                spinnerTimerMode.visibility = View.VISIBLE
            } else {
                timerModeStatus = false
                spinnerTimerMode.visibility = View.INVISIBLE
            }
        }
        //делаем файл для сохранения
        saveMainActSettingsFile = getSharedPreferences("Main_activity_settings", MODE_PRIVATE)

    }

    fun startButtonClick(view: View) {
        startGame()
    }

    private fun startGame() {

        //стартуем активность игры, передаём статус сложной игры/статус таймера
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("hardModeStatus", hardModeStatus)
        intent.putExtra("timerModeStatus", timerModeStatus)

        val milliSeconds = (timerModeSeconds * 10) //таймер ведется в миллисекундах
        intent.putExtra("timerModeMilliSeconds", milliSeconds)
        intent.putExtra("numOfQuestionsAtStart", numOfQuestionsAtStart)
        intent.putExtra("restartCheck", false)
        startActivity(intent)
        setSettingsToFile()
    }


    //сохраняем значения настроек игры
    private fun setSettingsToFile() {
        val editor = saveMainActSettingsFile.edit()
        editor.putInt(NUM_OF_QUESTIONS, numOfQuestionsAtStart).apply()
        editor.putInt(TIMER_MODE_SECONDS, timerModeSeconds).apply()
        editor.putBoolean(HARD_MODE_STATUS, hardModeStatus).apply()
        editor.putBoolean(TIMER_MODE_STATUS, timerModeStatus).apply()
    }

    //восстанавливаем значения настроек игры
    private fun getSettingsFromFile() {
        if (saveMainActSettingsFile.contains(TIMER_MODE_STATUS)) {
            numOfQuestionsAtStart = saveMainActSettingsFile.getInt(NUM_OF_QUESTIONS, 0)
            timerModeSeconds = saveMainActSettingsFile.getInt(TIMER_MODE_SECONDS, 0)
            hardModeStatus = saveMainActSettingsFile.getBoolean(HARD_MODE_STATUS, false)
            timerModeStatus = saveMainActSettingsFile.getBoolean(TIMER_MODE_STATUS, false)
        }
        overdrawInterfaceBySettings()
    }

    //отрисовываем интерфейс исходя из восстановленных настроек
    private fun overdrawInterfaceBySettings() {
        switchButtonHardMode.isChecked = hardModeStatus
        switchButtonTimerMode.isChecked = timerModeStatus
        for ((index, num) in resources.getStringArray(R.array.NumQuestionsSpinner).withIndex()) {
            if (numOfQuestionsAtStart == num.toInt()) {
                spinnerQuestionNum.setSelection(index)
            }
        }
        for ((index, num) in resources.getStringArray(R.array.TimerModeSpinnerArray).withIndex()) {
            if (timerModeSeconds == num.toInt()) {
                spinnerTimerMode.setSelection(index)
            }
        }
    }

    private fun checkActivityName() {
        if (saveMainActSettingsFile.contains(ACTIVITY_NAME)) {
            val nameOfActivity = saveMainActSettingsFile.getString(ACTIVITY_NAME, "main_activity")
            when {
                nameOfActivity?.contains("game_activity") == true -> {
                    intent.putExtra("restartCheck", true)
                    startActivity(Intent(this, GameActivity::class.java))
                }
                nameOfActivity?.contains("score_activity") == true -> {
                    val intent = Intent(this, ScoreGameActivity::class.java)
                    intent.putExtra("restartCheck", true)
                    startActivity(intent)
                }
            }
        }
    }

    fun hardModeQuestionMarkButtonClick(view: View) {
        val dialog = MyDialogFragments()
        val manager = supportFragmentManager
        dialog.setTitleAndMessage(
            getString(R.string.hard_mode_title_dialog_main),
            getString(R.string.hard_mode_dialog_message_main)
        )
        dialog.show(manager, "")
    }

    fun timerModeQuestionMarkButtonClick(view: View) {
        val dialog = MyDialogFragments()
        val manager = supportFragmentManager
        dialog.setTitleAndMessage(
            getString(R.string.timer_mode_title),
            getString(R.string.timer_mode_dialog_message_main)
        )
        dialog.show(manager, "")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
        getSettingsFromFile()
        checkActivityName()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
        getSettingsFromFile()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
        setSettingsToFile()
    }

    override fun onStop() {
        super.onStop()
        val editor = saveMainActSettingsFile.edit()
        editor.putString(ACTIVITY_NAME, "main_activity").apply()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}



