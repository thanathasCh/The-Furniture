package com.example.furnitureapp.models

class Address{
    var name: String? = null
    var road : String? = null
    var house : String? = null
    var sub_district : String? = null
    var district : String? = null
    var province: String? = null
    var isCurrentAddress: Boolean? = false
    var phoneNumber:String? = null


    constructor(name:String, road:String, house:String, sub_district:String, district:String, provice:String,isCurrentAddress:Boolean,phoneNumber:String){
        this.name = name
        this.road = road
        this.house = house
        this.sub_district = sub_district
        this.district = district
        this.province = provice
        this.isCurrentAddress = isCurrentAddress
        this.phoneNumber = phoneNumber
    }

}