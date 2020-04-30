package com.example.furnitureapp.data.local

import android.content.Context
import android.util.Log
import com.example.furnitureapp.services.fromJson
import com.example.furnitureapp.models.CartViewModel
import com.google.gson.Gson

class CartSharedPreference(private val context: Context) {
    private val sharedPreferenceKey = "Cart"
    private val sharedPreferenceCart = "UserCart"

    // save carts to shared preference
    fun saveCarts(carts: ArrayList<CartViewModel>) {
        val cartJson = Gson().toJson(carts)
//        Log.e("CARTS", cartJson)
        with(context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit()) {
            putString(sharedPreferenceCart, cartJson)
            apply()
        }
    }

    // get carts from shared preference
    fun retrieveCarts(): ArrayList<CartViewModel> {
        val cartJson = context.getSharedPreferences(
            sharedPreferenceKey, Context.MODE_PRIVATE
        ).getString(sharedPreferenceCart, "")
//        Log.e("CARTS", cartJson)
        return if (cartJson.isNullOrEmpty()) {
            ArrayList()
        } else {
            Gson().fromJson<ArrayList<CartViewModel>>(cartJson)
        }
    }

    // add cart to shared preference
    fun addCart(productId: String): Boolean {
        val carts = retrieveCarts()

        for (i in carts) {
            if (i.ProductId == productId) {
                i.Quantity += 1
                saveCarts(carts)
                return true
            }
        }
        return false
    }

    // update cart in shared preference
    fun updateCart(productId: String, quantity: Int): Boolean {
        val carts = retrieveCarts()

        for (i in carts) {
            if (i.ProductId == productId) {
                i.Quantity = quantity
                saveCarts(carts)
                return true
            }
        }
        return false
    }
}