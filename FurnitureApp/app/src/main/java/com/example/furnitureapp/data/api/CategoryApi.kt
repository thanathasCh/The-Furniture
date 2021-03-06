package com.example.furnitureapp.data.api

import com.example.furnitureapp.models.CategoryViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class CategoryApi(val db: CollectionReference = FirebaseFirestore.getInstance().collection("Categories")) {
    fun getCategories(callback: (ArrayList<CategoryViewModel>) -> Unit) {
        val categories = ArrayList<CategoryViewModel>()
        db.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (item in it.result!!) {
                    val bufferCategory = item.toObject(CategoryViewModel::class.java)
                    bufferCategory.Id = item.id
                    categories.add(bufferCategory)
                }
                callback(categories)
            } else {
                callback(categories)
            }
        }
    }
}
