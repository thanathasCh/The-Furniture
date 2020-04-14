package com.example.furnitureapp.data.api

import com.example.furnitureapp.models.ProductViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ProductApi(val db: CollectionReference = FirebaseFirestore.getInstance().collection("Products")) {
    fun getProducts(callback: (ArrayList<ProductViewModel>) -> Unit) {
        val products = ArrayList<ProductViewModel>()
        db.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (item in it.result!!) {
                    val bufferProduct = item.toObject(ProductViewModel::class.java)
                    bufferProduct.Id = item.id
                    products.add(bufferProduct)
                }
                callback(products)
            } else {
                callback(products)
            }
        }
    }
}