package com.example.furnitureapp.models

import com.google.firebase.database.Exclude
import java.util.*
import kotlin.collections.ArrayList

data class TransactionViewModel (
    @Exclude
    var Id: String = "",
    var UserId: String = "",
    var PaymentMethod: Int = -1,
    var TotalAmount: Double = 0.0,
    var IsPaid: Boolean = false,
    var PaidAt: Date? = null,
    var IsReserved: Boolean = false,
    var ReservedAt: Date? = null,
    var IsDelivered: Boolean = false,
    var DeliveredAt: Date? = null,
    var IsReceived: Boolean = false,
    var ReceivedAt: Date? = null,
    var AddressId: String = "",
    var IsPickup: Boolean = false,
    var TransactionItems: ArrayList<TransactionItemViewModel> = arrayListOf()
    ) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf (
            "UserId" to UserId,
            "PaymentMethod" to PaymentMethod,
            "TotalAmount" to TotalAmount,
            "IsPaid" to IsPaid,
            "PaidAt" to PaidAt,
            "IsReserved" to IsReserved,
            "ReservedAt" to ReservedAt,
            "IsDelivered" to IsDelivered,
            "DeliveredAt" to DeliveredAt,
            "AddressId" to AddressId,
            "IsPickup" to IsPickup,
            "TransactionItems" to TransactionItems.map { x -> x.toMap() }
        )
    }
}

data class TransactionItemViewModel (
    var Quantity: Int = 0,
    var TotalAmount: Double = 0.0,
    var Product: ProductViewModel = ProductViewModel()
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "Product" to Product.toMap(),
            "Quantity" to Quantity,
            "TotalAmount" to TotalAmount
        )
    }
}