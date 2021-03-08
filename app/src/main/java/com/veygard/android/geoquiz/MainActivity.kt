package com.veygard.android.geoquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private  lateinit var spinnerQuestionNum : Spinner
    private  lateinit var spinnerTimerMode : Spinner
    private var numOfQuestionsAtStart = 20
    private var timerModeSeconds = 60
    private lateinit var switchButtonHardMode: SwitchCompat
    private lateinit var switchButtonTimerMode: SwitchCompat
    private var hardModeStatus = false
    private var timerModeStatus = false



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
            if(isChecked){
                timerModeStatus = isChecked
                spinnerTimerMode.visibility = View.VISIBLE
            }
            else{
                timerModeStatus = false
                spinnerTimerMode.visibility = View.INVISIBLE
            }
        }
    }

    fun startButtonClick(view: View) {
        //стартуем активность игры, передаём статус сложной игры/статус таймера
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("hardModeStatus", hardModeStatus)
        intent.putExtra("timerModeStatus", timerModeStatus)
        intent.putExtra("timerModeSeconds", timerModeSeconds)
        startActivity(intent)

        //подготавливаем итоговый перечент вопросов
        formQuestionList(numOfQuestionsAtStart)
    }

    private fun formQuestionList(countQ : Int){
        val questionList = Question.getQuestionListAtStart()
        //получаем индексы для выгрузки радномного списка вопросов
        val indexStart= (Math.random() * (questionList.size-countQ)).toInt()
        val indexEnd = indexStart +countQ
        //формируем итоговый список вопросов для новой игры
        Question.questionListForGame = questionList.subList(indexStart, indexEnd)
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

    fun hardModeQuestionmarkButtonClick(view: View) {
        val dialog = MyDialogFragments()
        val manager = supportFragmentManager
        dialog.setTitleAndMessage(getString(R.string.hard_mode_title_dialog_main),getString(R.string.hard_mode_dialog_message_main))
        dialog.show(manager, "")
    }

    fun timerModeQuestionmarkButtonClick(view: View) {
        val dialog = MyDialogFragments()
        val manager = supportFragmentManager
        dialog.setTitleAndMessage(getString(R.string.timer_mode_title),getString(R.string.timer_mode_dialog_message_main))
        dialog.show(manager, "")
    }
}



