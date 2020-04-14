package com.example.furnitureapp.data.repository

import android.content.Context
import com.example.furnitureapp.data.api.CategoryApi
import com.example.furnitureapp.data.local.CategorySharedPreference
import com.example.furnitureapp.models.CategoryViewModel

class CategoryRepository(private val context: Context) {
    fun fetchCategory(isRemotePreferred: Boolean, callback: (ArrayList<CategoryViewModel>) -> Unit) {
        if (isRemotePreferred) {
            requestCategoryApi(callback)
        } else {
            if (isExisted()) {
                callback(CategorySharedPreference(context).retrieveCategories())
            } else {
                requestCategoryApi(callback)
            }
        }
    }

    private fun requestCategoryApi(callback: (ArrayList<CategoryViewModel>) -> Unit) {
        val announcementApi = CategoryApi()
        announcementApi.getCategories {
            saveCategory(it)
            callback(it)
        }
    }

    private fun isExisted(): Boolean {
        return context.getSharedPreferences(
            "Category",
            Context.MODE_PRIVATE).getString("AllCategory", "")!!.isNotBlank()
    }

    private fun saveCategory(announcements: ArrayList<CategoryViewModel>) {
        CategorySharedPreference(context).saveCategories(announcements)
    }
}