package com.example.furnitureapp.models

import android.app.Application
import com.example.furnitureapp.R

class CategoriesController : Application() {
    var categoriesList = ArrayList<Categories>()

    fun getCategories(): ArrayList<Categories> {
        categoriesList.add(
            Categories(
                "Table",
                R.drawable.desk
            )
        )
        categoriesList.add(
            Categories(
                "Chair",
                R.drawable.chair
            )
        )
        categoriesList.add(
            Categories(
                "Matress",
                R.drawable.mattress
            )
        )
        categoriesList.add(
            Categories(
                "Closet",
                R.drawable.closet
            )
        )
        categoriesList.add(
            Categories(
                "Bed",
                R.drawable.bed
            )
        )
        categoriesList.add(
            Categories(
                "More",
                R.drawable.more
            )
        )
        return categoriesList
    }
}