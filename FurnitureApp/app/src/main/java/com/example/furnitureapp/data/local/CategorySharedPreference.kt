package com.example.furnitureapp.data.local

import android.content.Context
import com.example.furnitureapp.services.fromJson
import com.example.furnitureapp.models.CategoryViewModel
import com.google.gson.Gson

class CategorySharedPreference(private val context: Context) {
    private val sharedPreferenceKey = "Category"
    private val sharedPreferenceCategoryKey = "AllCategory"

    fun saveCategories(announcements: ArrayList<CategoryViewModel>) {
        val categoryJson = Gson().toJson(announcements)
        with(context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit()) {
            putString(sharedPreferenceCategoryKey, categoryJson)
            apply()
        }
    }

    fun retrieveCategories(): ArrayList<CategoryViewModel> {
        val categoryJson =
            context.getSharedPreferences(
                sharedPreferenceKey,
                Context.MODE_PRIVATE).getString(sharedPreferenceCategoryKey, "")

        return if (categoryJson.isNullOrEmpty()) {
            ArrayList()
        } else {
            Gson().fromJson<ArrayList<CategoryViewModel>>(categoryJson)
        }
    }
}