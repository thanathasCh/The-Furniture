package com.example.furnitureapp.models

import com.google.firebase.database.Exclude
import java.util.*
import kotlin.collections.ArrayList

data class UserViewModel (
    var Id: String = "",
    var FirstName: String = "",
    var LastName: String = "",
    var Gender: Int = -1, // 0 = male, 1 = female, else = undefined
    var UserName: String = "",
    var Password: String = "",
    var Email: String = "",
    var TelephoneNumber: String = "",
    var ImageUrl: String = "",
    var CreatedAt: Date = Date(),
    var UpdatedAt: Date = Date()
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "FirstName" to FirstName,
            "LastName" to LastName,
            "Gender" to Gender,
            "Username" to UserName,
            "Password" to Password,
            "Email" to Email,
            "TelephoneNumber" to TelephoneNumber,
            "ImageUrl" to ImageUrl,
            "CreatedAt" to CreatedAt,
            "UpdatedAt" to UpdatedAt
        )
    }

    @Exclude
    fun getFullName() = "$FirstName $LastName"

    @Exclude
    fun genderText(): String {
        return when(Gender) {
            0 -> "Male"
            1 -> "Female"
            else -> "Undefined"
        }
    }
}