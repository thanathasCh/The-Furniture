package com.example.furnitureapp.services

import android.content.Context
import android.widget.Toast
import com.example.furnitureapp.views.main.MainActivity

class ToastBuilder {
    fun createLongToast(message: String) {
        val toast = Toast.makeText(MainActivity.mainThis, message, Toast.LENGTH_LONG)
        toast.show()
    }

    fun createShortToast(message: String) {
        val toast = Toast.makeText(MainActivity.mainThis, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}