package com.example.furnitureapp.data.api

import com.example.furnitureapp.models.CartViewModel
import com.example.furnitureapp.models.ProductViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class CartApi(private val db: CollectionReference = FirebaseFirestore.getInstance().collection("Carts")) {

    fun getCartByUserId(userId: String, callback: (ArrayList<CartViewModel>) -> Unit) {
        getCarts(userId) {
            getCarts(it) {carts ->
                callback(carts)
            }
        }
    }

    fun addCart(userId: String, productId: String, callback: (Boolean) -> Unit) {
        val newCart = CartViewModel(UserId = userId, ProductId = productId, Quantity = 1)
        db.add(newCart.toMap()).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true)
            } else {
                callback(false)
            }
        }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun updateCart(id: String, quantity: Int, callback: (Boolean) -> Unit) {
        db.document(id).update("Quantity", quantity)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun removeCart(id: String, callback: (Boolean) -> Unit) {
        db.document(id).delete()
            .addOnCompleteListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(true)
            }
    }

    private fun getCarts(userId: String, callback: (ArrayList<CartViewModel>) -> Unit) {
        val carts = ArrayList<CartViewModel>()
        db.whereEqualTo("UserId", userId)
            .get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (item in it.result!!) {
                    val bufCart = item.toObject(CartViewModel::class.java)
                    bufCart.Id = item.id
                    carts.add(bufCart)
                }
                callback(carts)
            } else {
                callback(carts)
            }
        }
    }

    private fun getCarts(items: ArrayList<CartViewModel>, callback: (ArrayList<CartViewModel>) -> Unit) {
        val productIds = ArrayList(items.map { it.ProductId })
        ProductApi().getProductByIds(productIds) {
            for (item in items) {
                item.Product = it.find { x -> x.Id == item.ProductId } ?: ProductViewModel()
            }

            callback(items)
        }
    }
}
