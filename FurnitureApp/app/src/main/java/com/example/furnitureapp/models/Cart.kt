package com.example.furnitureapp.models

import com.google.firebase.firestore.DocumentReference

data class CartViewModel (
    var Id: String? = null,
    var Quantity: Int = 0,
    var UserId: String? = null,
    var ProductIds: ArrayList<String> = arrayListOf()
)