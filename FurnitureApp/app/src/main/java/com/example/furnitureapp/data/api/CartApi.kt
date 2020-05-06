package com.example.furnitureapp.data.api

import android.util.Log
import com.example.furnitureapp.models.CartViewModel
import com.example.furnitureapp.models.ProductViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Document

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

    fun removeCarts(ids: ArrayList<String>, callback: (Boolean) -> Unit) {
        val batch = FirebaseFirestore.getInstance().batch()
        for (id in ids) {
            batch.delete(db.document(id))
        }

        batch.commit()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun isExisted(userId: String, productId: String, callback: (String) -> Unit) {
        db.whereEqualTo("UserId", userId)
            .whereEqualTo("ProductId", productId)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful && it.result != null && !it.result!!.isEmpty) {
                    for (item in it.result!!) {
                        callback(item.id)
                    }
                } else {
                    callback("")
                }
            }
            .addOnFailureListener {
                callback("")
            }
    }

    fun getQuantityById(id: String, callback: (Int) -> Unit) {
        db.document(id)
            .get()
            .addOnCompleteListener {
                callback(it.result!!["Quantity"].toString().toInt())
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
