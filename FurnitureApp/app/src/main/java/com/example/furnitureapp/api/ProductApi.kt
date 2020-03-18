package com.example.furnitureapp.api

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ProductApi(val db: CollectionReference = FirebaseFirestore.getInstance().collection("Products")) {
    fun getCategories(callback: (ArrayList<CategoryViewModel>) -> Unit) {
        val categories = ArrayList<CategoryViewModel>()
        db.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (item in it.result!!) {
                    categories.add(item.toObject(CategoryViewModel::class.java))
                }
                callback(categories)
            } else {
                callback(categories)
            }
        }
    }
}