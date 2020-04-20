package com.example.furnitureapp.services

import android.app.AlertDialog
import com.example.furnitureapp.views.main.MainActivity

class AlertBuilder {
    private val context = MainActivity.mainThis

    fun showOkAlert(title: String) {
        val builder = AlertDialog.Builder(context)

        with (builder) {
            setTitle(title)
            setPositiveButton("Okay") { _, _ ->  }
            show()
        }
    }

    fun showOkAlert(title: String, opt: () -> Unit) {
        val builder = AlertDialog.Builder(context)

        with (builder) {
            setTitle(title)
            setPositiveButton("Okay") { _, _ ->
                opt()
            }
            show()
        }
    }

    fun showYesNoAlert(title: String, yesOpt: () -> Unit) {
        val builder = AlertDialog.Builder(context)

        with (builder) {
            setTitle(title)
            setPositiveButton("Yes") { _, _ ->
                yesOpt()
            }
            setNegativeButton("No") { _, _ -> }
            show()
        }
    }

    fun showYesNoAlert(title: String, yesOpt: () -> Unit, noOpt: () -> Unit) {
        val builder = AlertDialog.Builder(context)

        with (builder) {
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