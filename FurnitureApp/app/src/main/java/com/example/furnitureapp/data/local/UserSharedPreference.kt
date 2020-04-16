package com.example.furnitureapp.data.local

import android.content.Context
import com.example.furnitureapp.fromJson
import com.example.furnitureapp.models.UserViewMode
import com.google.gson.Gson

class UserSharedPreference(private val context: Context) {
    private val sharedPreferenceKey = "User"
    private val isLogin = "Logged"
    private val userInfo = "UserInfo"

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

    fun saveUser(user: UserViewMode) {
        val userJson = Gson().toJson(user)
        with(context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit()) {
            putString(userInfo, userJson)
            apply()
        }
    }

    fun retrieveUser(): UserViewMode {
        val userJson =
            context.getSharedPreferences(
                sharedPreferenceKey,
                Context.MODE_PRIVATE).getString(userInfo, "")

        return if (userJson.isNullOrEmpty()) {
            UserViewMode()
        } else {
            Gson().fromJson<UserViewMode>(userJson)
        }
    }
}