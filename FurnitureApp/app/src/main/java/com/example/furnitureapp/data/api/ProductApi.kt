package com.example.furnitureapp.data.api

import android.util.Log
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
        }.addOnFailureListener {
            callback(products)
        }
    }

    fun getProductById(id: String, callback: (ProductViewModel) -> Unit) {
        var product = ProductViewModel()
        db.document(id)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    product = it.result!!.toObject(ProductViewModel::class.java) ?: ProductViewModel()
                    product.Id = it.result!!.id
                    callback(product)
                    } else {
                        callback(product)
                    }
            }.addOnFailureListener {
                callback(product)
            }
    }

    fun getProductByCategoryId(id: String, callback: (ArrayList<ProductViewModel>) -> Unit) {
        val products = ArrayList<ProductViewModel>()
        db.whereEqualTo("CategoryId", id)
            .get().addOnCompleteListener {
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
            }.addOnFailureListener {
                callback(products)
            }
    }

    fun getProductByIds(ids: ArrayList<String>, callback: (ArrayList<ProductViewModel>) -> Unit) {
        val products = ArrayList<ProductViewModel>()
        db.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (item in it.result!!) {
                    if (item.id in ids) {
                        val bufferProduct = item.toObject(ProductViewModel::class.java)
                        bufferProduct.Id = item.id
                        products.add(bufferProduct)
                    }
                }
                callback(products)
            } else {
                callback(products)
            }
        }.addOnFailureListener {
            callback(products)
            }
    }
}