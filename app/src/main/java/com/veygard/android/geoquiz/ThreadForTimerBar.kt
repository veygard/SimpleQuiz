package com.veygard.android.geoquiz

import android.os.Handler
import android.widget.ProgressBar
import android.widget.TextView
import java.text.DecimalFormat

//отдельный поток для прогресс бара таймера
//в конструкторе инстансы прогресс бара, текст-вью для отображения значений, значение для отсчета,
// инстанс активности в которой будем создавать поток
class ThreadForTimerBar(
    private val timerProgressBar: ProgressBar,
    private val timerTextView: TextView,
    private var milliSecondsLeft: Int,
    private var milliSecondsMax: Int,
    private val gameActivity: GameActivity
) : Runnable {

    companion object {
        //нужен для остановки цикла и завершения потока
        var interruptedForTimerBar = false
    }

    private val handler = Handler() //для задержки
    private val formatNum = DecimalFormat("0.#")
    private var doubleNum: Double = 0.0
    private var editor = GameActivity.saveGameActStatusFile.edit()
    override fun run() {
        timerProgressBar.max = milliSecondsMax

        editor.putInt(GameActivity.TIMER_MILLISECONDS_MAX, milliSecondsMax).apply()

        while (!interruptedForTimerBar && milliSecondsLeft >= 0) {
            handler.post {
                doubleNum = (milliSecondsLeft).toDouble() / 10 //для отображения в десятичном виде
                timerProgressBar.progress = milliSecondsLeft
                timerTextView.text = formatNum.format(doubleNum).toString()
                if (milliSecondsLeft == 0) { //если таймер закночился - завершаем
                    interruptedForTimerBar = true
                    milliSecondsLeft-- //для того чтобы сработала еще 1 проверка проверка
                    if (gameActivity.quizViewModel.hardModeStatus) {
                        gameActivity.gameOver()
                    }
                    gameActivity.quizViewModel.answerAlreadyDone = true
                    gameActivity.nextQuestion()
                }
            }
            milliSecondsLeft--
            //для того чтобы таймер не сбрасывался при закрытии приложения
            editor.putInt(GameActivity.TIMER_MILLISECONDS_LEFT, milliSecondsLeft).apply()
            try {
                Thread.sleep(100)
            } catch (e: InterruptedException) {
            }
        }
    }
}