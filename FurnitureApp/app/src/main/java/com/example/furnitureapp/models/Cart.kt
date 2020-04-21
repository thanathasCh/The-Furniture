package com.example.furnitureapp.models

import com.google.firebase.database.Exclude

data class CartViewModel (
    var Id: String = "",
    val ProductId: String = "",
    var Product: ProductViewModel = ProductViewModel(),
    var UserId: String = "",
    var Quantity: Int = 0
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "ProductId" to ProductId,
            "Quantity" to Quantity,
            "UserId" to UserId
        )
    }
}