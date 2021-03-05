package com.veygard.android.geoquiz

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class MyDialogFragments() : DialogFragment(){
    lateinit var title: String
    lateinit var message: String

        fun setTitleAndMessage(title: String, message: String){
            this.title = title
            this.message = message
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
                .setMessage(message)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}