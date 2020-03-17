package com.example.furnitureapp

import android.app.Application

class Singleton : Application() {
    lateinit var globalVar : ArrayList<Product>

}