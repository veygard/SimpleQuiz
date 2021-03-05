package com.veygard.android.geoquiz

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class BackPressedDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Завершение игры")
                .setMessage("Вы точно хотите завершить игру?")
                .setPositiveButton("Да"
                ) { _, _->
                    (activity as GameActivity?)?.startOver()
                }
                .setNegativeButton("Нет"){ dialog, _ ->  dialog.cancel()}
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}