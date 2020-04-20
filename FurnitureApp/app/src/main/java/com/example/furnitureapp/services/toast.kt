package com.example.furnitureapp.services

import android.content.Context
import android.widget.Toast

fun createLongToast(context: Context, message: String) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    toast.show()
}

fun createShortToast(context: Context, message: String) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast.show()
}