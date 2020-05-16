package com.example.furnitureapp.services

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import com.example.furnitureapp.R
import com.example.furnitureapp.views.main.MainActivity
import kotlinx.android.synthetic.main.ok_dialog.*
import kotlinx.android.synthetic.main.yes_no_dialog.*

class AlertBuilder {
    private val context = MainActivity.mainThis
    val dialogs = Dialog(context)

    fun showOkAlertWithAction(title: String, description: String): Dialog {
        dialogs.setContentView(R.layout.ok_dialog)
        dialogs.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogs.show()
        dialogs.ok_title.text = title
        dialogs.ok_description.text = description
        return dialogs
    }

    fun showOkAlert(title: String, description: String): Dialog {
        dialogs.setContentView(R.layout.ok_dialog)
        dialogs.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogs.show()

        dialogs.ok_btn.setOnClickListener {
            dismiss()
        }

        dialogs.title.text = title
        dialogs.description.text = description

        return dialogs
    }

    fun dismiss() {
        dialogs.dismiss()
    }

    fun showYesNoAlert(title: String, description: String): Dialog {
        dialogs.setContentView(R.layout.yes_no_dialog)
        dialogs.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogs.show()

        dialogs.no_btn.setOnClickListener {
            dismiss()
        }

        dialogs.title.text = title
        dialogs.description.text = description

        return dialogs
    }

    fun showYesNoAlertWithAction(title: String, description: String): Dialog {
        dialogs.setContentView(R.layout.yes_no_dialog)
        dialogs.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogs.show()

        dialogs.title.text = title
        dialogs.description.text = description

        return dialogs
    }
}