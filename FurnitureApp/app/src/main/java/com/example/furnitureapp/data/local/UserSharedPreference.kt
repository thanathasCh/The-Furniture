package com.example.furnitureapp.data.local

import android.content.Context
import com.example.furnitureapp.services.fromJson
import com.example.furnitureapp.models.UserViewModel
import com.google.gson.Gson

class UserSharedPreference(private val context: Context) {
    private val sharedPreferenceKey = "User"
    private val isLogin = "Logged"
    private val userInfo = "UserInfo"

    fun logIn() {
        with(context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit()) {
            putBoolean(isLogin, true)
            apply()
        }
    }

    fun isLogin(): Boolean {
        return context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE)
            .getBoolean(isLogin, false)
    }

    fun logOut() {
        context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE)
            .edit().clear().apply()
    }

    fun saveUser(user: UserViewModel) {
        val userJson = Gson().toJson(user)
        with(context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit()) {
            putString(userInfo, userJson)
            apply()
        }
    }

    fun getUserId(): String {
        return retrieveUser().Id ?: ""
    }

    fun retrieveUser(): UserViewModel {
        val userJson =
            context.getSharedPreferences(
                sharedPreferenceKey,
                Context.MODE_PRIVATE).getString(userInfo, "")

        return if (userJson.isNullOrEmpty()) {
            UserViewModel()
        } else {
            Gson().fromJson<UserViewModel>(userJson)
        }
    }
}