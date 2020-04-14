package com.example.furnitureapp.data.local

import android.content.Context
import com.google.gson.Gson

class UserSharedPreference(private val context: Context) {
    private val sharedPreferenceKey = "User"
    private val isLogin = "Logged"

    fun loggedIn() {
        with(context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit()) {
            putBoolean(isLogin, true)
            apply()
        }
    }

    fun isLogged(): Boolean {
        return context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE)
            .getBoolean(isLogin, false)
    }

    fun logOut() {
        context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE)
            .edit().clear().apply()
    }
}