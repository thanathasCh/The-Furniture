package com.example.furnitureapp.models

import android.app.Application

class CategoriesController : Application() {
    var categoriesList = ArrayList<Categories>()

    fun setCategories(categories:Categories) {
        categoriesList.add(categories)

    }
    fun getCategories():ArrayList<Categories>{
        return categoriesList
    }



}