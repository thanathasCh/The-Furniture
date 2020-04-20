package com.example.furnitureapp.models

import com.google.firebase.database.Exclude
import java.util.*
import kotlin.collections.ArrayList

class User{
    var id: String? = null
    var gender: String? = null
    var firstName: String? = null
    var lastName:String? = null
    var UserName: String? = null
    var password : String? = null
    var phoneNumber : String? = null
    var email : String? = null
    var addressList = ArrayList<Address>()
    var productList = ArrayList<Product>()



    constructor(id:String, gender:String,firstName:String, lastName:String, userName:String, password:String,phoneNumber:String, email:String, addressList:ArrayList<Address>, productList:ArrayList<Product> ){
        this.id = id
        this.gender = gender
        this.firstName = firstName
        this.lastName = lastName
        this.UserName = userName
        this.password = password
        this.phoneNumber = phoneNumber
        this.email = email
        this.addressList = addressList
        this.productList = productList
    }
}

data class UserViewModel (
    var Id: String? = null,
    var FirstName: String? = null,
    var LastName: String? = null,
    var Gender: Int = -1, // 0 = male, 1 = female, else = undefined
    var UserName: String? = null,
    var Password: String? = null,
    var Email: String? = null,
    var TelephoneNumber: String? = null,
    var ImageUrl: String? = null,
    var CreatedAt: Date? = null,
    var UpdatedAt: Date? = null
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