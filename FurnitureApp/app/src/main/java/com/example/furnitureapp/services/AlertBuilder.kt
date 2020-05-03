package com.example.furnitureapp.services

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import com.example.furnitureapp.R
import com.example.furnitureapp.views.main.MainActivity
import kotlinx.android.synthetic.main.yes_no_dialog.*

class AlertBuilder {
    private val context = MainActivity.mainThis
    val dialogs = Dialog(context)

    fun showOkAlert(title: String) {
        val builder = AlertDialog.Builder(context)

        with(builder) {
            setTitle(title)
            setPositiveButton("Okay") { _, _ -> }
            show()
        }
    }

    fun showOkAlert(title: String, opt: () -> Unit) {
        val builder = AlertDialog.Builder(context)

        with(builder) {
            setTitle(title)
            setPositiveButton("Okay") { _, _ ->
                opt()
            }
            show()
        }
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

    fun showYesNoAlert(title: String, yesOpt: () -> Unit, noOpt: () -> Unit) {
        val builder = AlertDialog.Builder(context)

        with(builder) {
            setTitle(title)
            setPositiveButton("Yes") { _, _ ->
                yesOpt()
            }
            setNegativeButton("No") { _, _ ->
                noOpt()
            }
            show()
        }
    }
}