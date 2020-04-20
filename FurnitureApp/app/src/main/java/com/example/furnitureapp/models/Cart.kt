package com.example.furnitureapp.models

import com.google.firebase.database.Exclude

data class CartViewModel (
    var Id: String? = null,
    val ProductId: String? = null,
    var Product: ProductViewModel? = null,
    var UserId: String? = null,
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