package com.example.furnitureapp.views.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.furnitureapp.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME_OUT = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(
            {
                welcome_txt.text = "The Furniture"
                Handler().postDelayed({
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }, 2000L)

            }, SPLASH_TIME_OUT
        )
    }
}

