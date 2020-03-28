package com.example.furnitureapp.api

import com.example.furnitureapp.models.StoreViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class StoreApi(val db: CollectionReference = FirebaseFirestore.getInstance().collection("Store")) {
    fun getStores(callback: (ArrayList<StoreViewModel>) -> Unit) {
        val stores = ArrayList<StoreViewModel>()
        db.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (item in it.result!!) {
                    val bufferStore = item.toObject(StoreViewModel::class.java)
                    bufferStore.Id = item.id
                    stores.add(bufferStore)
                }
                callback(stores)
            } else {
                callback(stores)
            }
        }
    }
}