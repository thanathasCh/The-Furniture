package com.example.furnitureapp.data.local

import android.content.Context
import com.example.furnitureapp.fromJson
import com.example.furnitureapp.models.ProductViewModel
import com.google.gson.Gson

class ProductSharedPreference(private val context: Context) {
    private val sharedPreferenceKey = "Product"
    private val sharedPreferenceProductKey = "AllProduct"

    fun saveProducts(products: ArrayList<ProductViewModel>) {
        val productJson = Gson().toJson(products)
        with(context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit()) {
            putString(sharedPreferenceProductKey, productJson)
            apply()
        }
    }

    fun retrieveProducts(): ArrayList<ProductViewModel> {
        val productJson =
            context.getSharedPreferences(
                sharedPreferenceKey,
                Context.MODE_PRIVATE).getString(sharedPreferenceProductKey, "")

        return if (productJson.isNullOrEmpty()) {
            ArrayList()
        } else {
            Gson().fromJson<ArrayList<ProductViewModel>>(productJson)
        }
    }
}