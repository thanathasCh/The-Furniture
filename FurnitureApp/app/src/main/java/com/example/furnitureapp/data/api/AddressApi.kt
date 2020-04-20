package com.example.furnitureapp.data.api

import com.example.furnitureapp.models.AddressViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.security.auth.callback.Callback

class AddressApi(private val db: CollectionReference = FirebaseFirestore.getInstance().collection("Addresses")) {
    fun getAddressByUserId(userId: String, callback: (ArrayList<AddressViewModel>) -> Unit) {
        val addresses = ArrayList<AddressViewModel>()
        db.whereEqualTo("UserId", userId)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (item in it.result!!) {
                        val bufAddress = item.toObject(AddressViewModel::class.java)
                        bufAddress.Id = item.id
                        addresses.add(bufAddress)
                    }
                    callback(addresses)
                }
            }
            .addOnFailureListener {
                callback(addresses)
            }
    }

    fun addAddress(address: AddressViewModel, callback: (Boolean) -> Unit) {
        db.add(address.toMap())
            .addOnCompleteListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun removeAddress(id: String, callback: (Boolean) -> Unit) {
        db.document(id).delete()
          .addOnCompleteListener {
              callback(true)
          }
          .addOnFailureListener {
              callback(false)
          }
    }

    fun updateAddress(address: AddressViewModel, callback: (Boolean) -> Unit) {
        db.document(address.Id ?: "")
            .update(address.toMap())
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}