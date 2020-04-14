package com.example.furnitureapp.models

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