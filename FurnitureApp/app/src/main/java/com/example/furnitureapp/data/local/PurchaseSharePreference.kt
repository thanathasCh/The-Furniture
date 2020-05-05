package com.example.furnitureapp.data.local

import android.content.Context
import com.example.furnitureapp.models.CartViewModel
import com.example.furnitureapp.services.fromJson
import com.google.gson.Gson

class PurchaseSharePreference (private val context: Context) {
    private val sharedPreferenceKey = "Purchase"
    private val sharedPreferencePurchase = "CartPurchase"

    fun savePurchase(carts: ArrayList<CartViewModel>){
        val cartJson = Gson().toJson(carts)

        with(context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit()) {
            putString(sharedPreferencePurchase, cartJson)
            apply()
        }
    }

    fun retrievePurchase(): ArrayList<CartViewModel> {
        val cartJson = context.getSharedPreferences(
            sharedPreferenceKey, Context.MODE_PRIVATE
        ).getString(sharedPreferencePurchase, "")
//        Log.e("CARTS", cartJson)
        return if (cartJson.isNullOrEmpty()) {
            ArrayList()
        } else {
            Gson().fromJson<ArrayList<CartViewModel>>(cartJson)
        }
    }

    fun clearCart(){
        val sharedPref = context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()?.clear()
        editor?.apply()
    }
}