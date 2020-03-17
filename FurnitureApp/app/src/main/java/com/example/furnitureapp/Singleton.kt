package com.example.furnitureapp

import android.app.Application
import com.example.furnitureapp.models.Product

class Singleton : Application() {
    lateinit var globalVar : ArrayList<Product>

}