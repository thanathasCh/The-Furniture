package com.example.furnitureapp.services

import android.widget.Toast
import com.example.furnitureapp.views.main.MainActivity
import es.dmoral.toasty.Toasty

class ToastBuilder {
    fun createLongToast(message: String) {
        val toast = Toast.makeText(MainActivity.mainThis, message, Toast.LENGTH_LONG)
        toast.show()
    }

    fun createShortToast(message: String) {
        val toast = Toasty.success(MainActivity.mainThis, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}