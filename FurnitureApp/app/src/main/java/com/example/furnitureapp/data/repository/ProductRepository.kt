package com.example.furnitureapp.data.repository

import android.content.Context
import com.example.furnitureapp.data.api.ProductApi
import com.example.furnitureapp.data.local.ProductSharedPreference
import com.example.furnitureapp.models.ProductViewModel

class ProductRepository(private val context: Context) {
    fun fetchProducts(isRemotePreferred: Boolean, callback: (ArrayList<ProductViewModel>) -> Unit) {
        if (isRemotePreferred) {
            requestProductApi(callback)
        } else {
            if (isExisted()) {
                callback(ProductSharedPreference(context).retrieveProducts())
            } else {
                requestProductApi(callback)
            }
        }
    }

    fun fetchProductsByCategoryId(isRemotePreferred: Boolean, categoryId: String, callback: (ArrayList<ProductViewModel>) -> Unit) {
        if (isRemotePreferred) {
            requestProductApi(callback)
        } else {
            if (isExisted()) {
                callback(ProductSharedPreference(context).retrieveProducts())
            } else {
                requestProductApi(callback)
            }
        }
    }

    private fun requestProductByCategoryIdApi(id: String, callback: (ArrayList<ProductViewModel>) -> Unit) {
        val productApi = ProductApi()
        productApi.getProductByCategoryId(id) {
            saveProducts(it)
            callback(it)
        }
    }

    private fun requestProductApi(callback: (ArrayList<ProductViewModel>) -> Unit) {
        val announcementApi = ProductApi()
        announcementApi.getProducts {
            saveProducts(it)
            callback(it)
        }
    }

    private fun isExisted(): Boolean {
        return context.getSharedPreferences(
            "Product",
            Context.MODE_PRIVATE).getString("AllProduct", "")!!.isNotBlank()
    }

    private fun saveProducts(announcements: ArrayList<ProductViewModel>) {
        ProductSharedPreference(context).saveProducts(announcements)
    }
}