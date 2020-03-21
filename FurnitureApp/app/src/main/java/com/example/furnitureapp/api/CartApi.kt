package com.example.furnitureapp.api

import com.example.furnitureapp.models.CartViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class CartApi(val db: CollectionReference = FirebaseFirestore.getInstance().collection("Carts")) {
    fun getCart(callback: (ArrayList<CartViewModel>) -> Unit) {
        val carts = ArrayList<CartViewModel>()
        db.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (item in it.result!!) {
                    val bufferCart = item.toObject(CartViewModel::class.java)
                    bufferCart.Id = item.id
                    carts.add(bufferCart)
                }
                callback(carts)
            } else {
                callback(carts)
            }
        }
    }
}
