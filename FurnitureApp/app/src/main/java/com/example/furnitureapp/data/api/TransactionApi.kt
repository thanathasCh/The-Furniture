package com.example.furnitureapp.data.api

import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.models.TransactionViewModel
import com.example.furnitureapp.views.main.MainActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class TransactionApi(val db: CollectionReference = FirebaseFirestore.getInstance().collection("Transactions")) {

    // get user transaction or Purchase History
    fun getTransaction(callback: (ArrayList<TransactionViewModel>) -> Unit) {
        val userId = UserSharedPreference(MainActivity.mainThis).getUserId()
        val transaction = ArrayList<TransactionViewModel>()
        db.whereEqualTo("UserId", userId)
            .get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (item in it.result!!) {
                    val bufferTransaction = item.toObject(TransactionViewModel::class.java)
                    bufferTransaction.Id = item.id
                    transaction.add(bufferTransaction)
                }
                callback(transaction)
            } else {
                callback(transaction)
            }
        }
    }

    // add new transaction or Purchase History to db
    fun addTransaction(item: TransactionViewModel, callback: (Boolean) -> Unit) {
        db.add(item.toMap())
            .addOnCompleteListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}