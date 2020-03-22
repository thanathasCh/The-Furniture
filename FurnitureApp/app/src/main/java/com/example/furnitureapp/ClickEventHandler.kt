package com.example.furnitureapp

import android.view.View

interface ClickEventHandler {
    fun forwardClick(holder: View,id:String,name: String, size: String, code: String, price: Float,image: Int,material: String, available: Int)
}