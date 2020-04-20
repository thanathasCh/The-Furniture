package com.example.furnitureapp.data.repository

import android.content.Context
import com.example.furnitureapp.data.api.CartApi
import com.example.furnitureapp.data.local.CartSharedPreference
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.models.CartViewModel

class CartRepository(private val context: Context) {
    fun fetchCartByUserId(isRemotePreferred: Boolean, userId: String, callback: (ArrayList<CartViewModel>) -> Unit) {
        if (isRemotePreferred) {
            requestCartApi(userId, callback)
        } else {
            if (isExisted()) {
                callback(CartSharedPreference(context).retrieveCarts())
            } else {
                requestCartApi(userId, callback)
            }
        }
    }

    fun addCart(productId: String, callback: (Boolean) -> Unit) {
        if (UserSharedPreference(context).isLogin()) {
            val user = UserSharedPreference(context).retrieveUser()
            val cartApi = CartApi()
            cartApi.addCart(user.Id ?: "", productId) {
                if (it) {
                    cartApi.getCartByUserId(user.Id ?: "") { carts ->
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
        CartSharedPreference(context).saveCarts(carts)
    }
}