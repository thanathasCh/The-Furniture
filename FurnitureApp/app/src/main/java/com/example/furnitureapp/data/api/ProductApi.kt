package com.example.furnitureapp.data.api

import android.util.Log
import com.example.furnitureapp.models.CartViewModel
import com.example.furnitureapp.models.ProductViewModel
import com.google.common.math.Quantiles
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ProductApi(val db: CollectionReference = FirebaseFirestore.getInstance().collection("Products")) {
    // get product by name or search product
    fun getProductByName(name: String, callback: (ArrayList<ProductViewModel>) -> Unit) {
        db.orderBy("Name")
            .startAt(name)
            .endAt(name + '\uf8ff')
            .get()
            .addOnCompleteListener {
                for (i in it.result!!) {
                    Log.d("DEBUG", i.toObject(ProductViewModel::class.java).toString())
                }
            }
    }

    // get list of product names
    fun getProductNames(callback: (ArrayList<String>) -> Unit) {
        val names = ArrayList<String>()
        db.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (item in it.result!!) {
                    val product = item.toObject(ProductViewModel::class.java)
                    names.add(product.Name)
                }
                callback(names)
            } else {
                callback(names)
            }
        }.addOnFailureListener {
            callback(names)
        }
    }

    // get all products in db
    fun getProducts(callback: (ArrayList<ProductViewModel>) -> Unit) {
        val products = ArrayList<ProductViewModel>()
        db.get()
            .addOnCompleteListener {
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

    // get a specific product by id
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

    // get product by its category, pass category id
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

    // get a list of products by passing a list of product ids
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

    fun purchaseProducts(carts: ArrayList<CartViewModel>, callback: (ArrayList<String>) -> Unit) {
        val failedProducts = ArrayList<String>()

        val batch = FirebaseFirestore.getInstance().batch()
        val productIds = ArrayList(carts.map { it.ProductId })

        getProductByIds(productIds) { products ->
            for (i in carts) {
                val ref = db.document(i.ProductId)
                val quantity = (products.find { x -> x.Id == i.ProductId } ?: ProductViewModel()).ProductStock

                if (quantity >= i.Quantity) {
                    batch.update(ref, "ProductStock", quantity - i.Quantity)
                } else {
                    failedProducts.add(ref.id)
                }
            }

            if (failedProducts.isEmpty()) {
                batch.commit()
                    .addOnCompleteListener {
                        callback(failedProducts)
                    }
            } else {
                callback(failedProducts)
            }
        }
    }

    fun purchaseProduct(productId: String, quantityLeft: Int, callback: (Boolean) -> Unit) {
        db.document(productId)
            .update("ProductStock", quantityLeft)
            .addOnCompleteListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}