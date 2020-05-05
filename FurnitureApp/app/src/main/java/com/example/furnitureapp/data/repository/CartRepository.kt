package com.example.furnitureapp.data.repository

import android.content.Context
import android.util.Log.e
import com.example.furnitureapp.data.api.CartApi
import com.example.furnitureapp.data.local.CartSharedPreference
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.models.CartViewModel
import com.example.furnitureapp.views.main.MainActivity

class CartRepository(private val context: Context) {

    //Get Cart by user (don't have to pass parameter)
    fun fetchCartByUserId(isRemotePreferred: Boolean, callback: (ArrayList<CartViewModel>) -> Unit) {
        val userId = UserSharedPreference(MainActivity.mainThis).getUserId()
        e("user id ", userId)
        if (isRemotePreferred) {
            requestCartApi(userId, callback)
        } else {
            if (isExisted()) {
                callback(CartSharedPreference(context).retrieveLoginCart())
            } else {
                requestCartApi(userId, callback)
            }
        }
    }

    //Add Cart to db, use only product id
    fun addCart(productId: String, callback: (Boolean) -> Unit) {
        if (UserSharedPreference(context).isLogin()) {
            val user = UserSharedPreference(context).retrieveUser()
            val cartApi = CartApi()
            cartApi.addCart(user.Id ?: "", productId) {
                if (it) {
                    cartApi.getCartByUserId(user.Id) { carts ->
                        CartSharedPreference(context).saveCarts(carts)
                        callback(true)
                    }
                } else {
                    callback(false)
                }
            }
        } else {
            callback(CartSharedPreference(context).addCart(productId))
        }
    }

    // update quantity of specific product in cart, pass product id and quantity
    fun updateCart(id: String, quantity: Int, callback: (Boolean) -> Unit) {
        if (UserSharedPreference(context).isLogin()) {
            val user = UserSharedPreference(context).retrieveUser()
            val cartApi = CartApi()

            cartApi.updateCart(user.Id ?: "", quantity) {
                if (it) {
                    cartApi.getCartByUserId(user.Id ?: "") { cart ->
                        CartSharedPreference(context).saveCarts(cart)
                        callback(true)
                    }
                } else {
                    callback(false)
                }
            }
        } else {
            callback(CartSharedPreference(context).updateCart(id, quantity))
        }
    }

    private fun requestCartApi(userId: String, callback: (ArrayList<CartViewModel>) -> Unit) {
        val cartApi = CartApi()
        cartApi.getCartByUserId(userId) {
            saveCart(it)
            callback(it)
        }
    }

    private fun isExisted(): Boolean {
        return context.getSharedPreferences(
            "Cart",
            Context.MODE_PRIVATE).getString("UserCart", "")!!.isNotBlank()
    }

    private fun saveCart(carts: ArrayList<CartViewModel>) {
        CartSharedPreference(context).saveLoginCart(carts)
    }
}