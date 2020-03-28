package com.example.furnitureapp.api

import com.example.furnitureapp.models.PaymentMethodViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class PaymentMethodApi(val db: CollectionReference = FirebaseFirestore.getInstance().collection("PaymentMethods")) {
    fun getPaymentMethods(callback: (ArrayList<PaymentMethodViewModel>) -> Unit) {
        val paymentMethods = ArrayList<PaymentMethodViewModel>()
        db.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (item in it.result!!) {
                    val bufferPaymentMethod = item.toObject(PaymentMethodViewModel::class.java)
                    bufferPaymentMethod.Id = item.id
                    paymentMethods.add(bufferPaymentMethod)
                }
                callback(paymentMethods)
            } else {
                callback(paymentMethods)
            }
        }
    }
}