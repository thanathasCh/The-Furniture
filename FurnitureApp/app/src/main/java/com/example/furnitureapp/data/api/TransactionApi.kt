package com.example.furnitureapp.data.api

import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.models.*
import com.example.furnitureapp.views.main.MainActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

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

    fun addCartsToTransaction(carts: ArrayList<CartViewModel>, callback: (Boolean) -> Unit) {
        val userId = UserSharedPreference(MainActivity.mainThis).getUserId()
        val transaction = TransactionViewModel (
            UserId = userId,
            PaymentMethod = 0,
            TotalAmount = carts.sumByDouble { x -> x.Product.Price * x.Quantity },
            IsPaid = true
        )

        for (cart in carts) {
            transaction.TransactionItems.add(TransactionItemViewModel(
                Product = cart.Product,
                Quantity = cart.Quantity,
                TotalAmount = cart.Product.Price * cart.Quantity)
            )
        }

        addTransaction(transaction, callback)
    }
}