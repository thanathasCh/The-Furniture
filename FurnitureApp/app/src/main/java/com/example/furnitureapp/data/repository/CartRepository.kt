package com.example.furnitureapp.data.repository

import android.content.Context
import android.util.Log.e
import com.example.furnitureapp.data.api.CartApi
import com.example.furnitureapp.data.api.ProductApi
import com.example.furnitureapp.data.api.TransactionApi
import com.example.furnitureapp.data.local.CartSharedPreference
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.models.CartViewModel
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.models.TransactionItemViewModel
import com.example.furnitureapp.models.TransactionViewModel
import com.example.furnitureapp.views.main.MainActivity
import java.util.*
import kotlin.collections.ArrayList

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
            val userId = UserSharedPreference(context).getUserId()
            val cartApi = CartApi()

            cartApi.isExisted(userId, productId) {
                if (it.isEmpty()) {
                    cartApi.addCart(userId, productId) { it2 ->
                        if (it2) {
                            cartApi.getCartByUserId(userId) { carts ->
                                CartSharedPreference(context).saveCarts(carts)
                                callback(true)
                            }
                        } else {
                            callback(false)
                        }
                    }
                } else {
                    cartApi.getQuantityById(it) { quantity ->
                        cartApi.updateCart(it, quantity + 1) { it3 ->
                            if (it3) {
                                cartApi.getCartByUserId(userId) { carts ->
                                    CartSharedPreference(context).saveCarts(carts)
                                    callback(true)
                                }
                            } else {
                                callback(false)
                            }
                        }
                    }
                }
            }
        } else {
            callback(CartSharedPreference(context).addCart(productId))
        }
    }

    // update quantity of specific product in cart, pass product id and quantity
    fun updateCart(id: String, quantity: Int, callback: (Boolean) -> Unit) {
        if (UserSharedPreference(context).isLogin()) {
            val userId = UserSharedPreference(context).getUserId()
            val cartApi = CartApi()

            cartApi.updateCart(id, quantity) {
                if (it) {
                    cartApi.getCartByUserId(userId) { cart ->
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

    fun removeCart(id: String, callback: (Boolean) -> Unit) {
        val userId = UserSharedPreference(context).getUserId()
        val cartApi = CartApi()

        cartApi.removeCart(id) {
            if (it) {
                cartApi.getCartByUserId(userId) { cart ->
                    CartSharedPreference(context).saveCarts(cart)
                    callback(true)
                }
            } else {
                callback(false)
            }
        }
    }

    fun removeCarts(ids: ArrayList<String>, callback: (Boolean) -> Unit) {
        val userId = UserSharedPreference(context).getUserId()
        val cartApi = CartApi()

        cartApi.removeCarts(ids) {
            if (it) {
                cartApi.getCartByUserId(userId) { cart ->
                    CartSharedPreference(context).saveCarts(cart)
                    callback(true)
                }
            } else {
                callback(false)
            }
        }
    }

    private fun requestCartApi(userId: String, callback: (ArrayList<CartViewModel>) -> Unit) {
        val cartApi = CartApi()
        cartApi.getCartByUserId(userId) {
            saveCart(it)
            callback(it)
        }
    }

    fun purchaseCarts(carts: ArrayList<CartViewModel>, addressId: String, isPickUp: Boolean , callback: (ArrayList<String>?) -> Unit) {
        val productApi = ProductApi()
        val transactionApi = TransactionApi()
        val cartApi = CartApi()

        productApi.purchaseProducts(carts) {
            if (it.isEmpty()) {
                transactionApi.addCartsToTransaction(carts, addressId, isPickUp) { it2 ->
                    if (it2) {
                        cartApi.removeCarts(ArrayList(carts.map { x -> x.Id })) { it3 ->
                            if (it3) {
                                callback(it)
                            } else {
                                callback(null)
                            }
                        }
                    } else {
                        callback(null)
                    }
                }
            } else {
                callback(it)
            }
        }
    }

    fun purchaseProduct(productId: String, quantity: Int, addressId: String, isPickUp: Boolean, callback: (Boolean) -> Unit) {
        val productApi = ProductApi()
        val userId = UserSharedPreference(MainActivity.mainThis).getUserId()
        val transactionApi = TransactionApi()

        productApi.getProductById(productId) { product ->
            if (product.ProductStock >= quantity) {
                productApi.purchaseProduct(product.Id, product.ProductStock - quantity) {
                    if (it) {
                        val futureDate = Date()
                        futureDate.date = futureDate.date + 4

                        val transaction = TransactionViewModel (
                            UserId = userId,
                            TotalAmount = product.Price * quantity,
                            AddressId = addressId,
                            IsPickup = isPickUp,
                            PaidAt = Date(),
                            ReceivedAt = if (isPickUp) null else futureDate
                        )

                        transaction.TransactionItems.add(TransactionItemViewModel(
                            Product = product,
                            Quantity = quantity,
                            TotalAmount = product.Price * quantity
                        ))

                        transactionApi.addTransaction(transaction, callback)
                    } else {
                        callback(false)
                    }
                }
            } else {
                callback(false)
            }
        }
    }

    /*

        for (cart in carts) {
            transaction.TransactionItems.add(TransactionItemViewModel(
                Product = cart.Product,
                Quantity = cart.Quantity,
                TotalAmount = cart.Product.Price * cart.Quantity)
            )
        }
     */

    private fun isExisted(): Boolean {
        return context.getSharedPreferences(
            "Cart",
            Context.MODE_PRIVATE).getString("UserCart", "")!!.isNotBlank()
    }

    private fun saveCart(carts: ArrayList<CartViewModel>) {
        CartSharedPreference(context).saveLoginCart(carts)
    }
}