package com.veygard.android.geoquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private  lateinit var spinnerQuestionNum : Spinner
    private var numOfQuestionsAtStart = 10
    private lateinit var switchButtonHardMode: SwitchCompat
    private var hardModeStatus = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_main)

        spinnerQuestionNum = findViewById(R.id.spinner_num_questions)
        spinnerQuestionNum.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
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
    }

    fun startButtonClick(view: View) {
        //стартуем активность игры, передаём статус сложной игры
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("hardModeStatus", hardModeStatus)
        startActivity(intent)

        //подготавливаем итоговый перечент вопросов
        formQuestionList(numOfQuestionsAtStart)
    }

    private fun formQuestionList(countQ : Int){
        val questionList = Question.getQuestionListAtStart()
        //получаем индексы для выгрузки радномного списка вопросов
        val indexStart= (Math.random() * (questionList.size-countQ)).toInt()
        val indexEnd = indexStart +countQ
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
        val title = getString(R.string.hard_mode_title_dialog_main)
        dialog.setTitleAndMessage(getString(R.string.hard_mode_title_dialog_main),getString(R.string.hard_mode_dialog_message_main))
        dialog.show(manager, "")
    }
}



