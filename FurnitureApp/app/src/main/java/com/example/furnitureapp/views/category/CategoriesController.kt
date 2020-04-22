package com.example.furnitureapp.views.category

import android.app.Application
import com.example.furnitureapp.models.Categories

class CategoriesController : Application() {
    var categoriesList = ArrayList<Categories>()

    fun setCategories(categories: Categories) {
        categoriesList.add(categories)

    }
    fun getCategories():ArrayList<Categories>{
        return categoriesList
    }



}