package com.example.furnitureapp.models

class Address{
    var id: String? = null
    var type:String? = null
    var name: String? = null
    var road : String? = null
    var house : String? = null
    var sub_district : String? = null
    var district : String? = null
    var province: String? = null
    var isCurrentAddress: Boolean? = false
    var phoneNumber:String? = null


    constructor(id:String,type:String,name:String, road:String, house:String, sub_district:String, district:String, provice:String,isCurrentAddress:Boolean,phoneNumber:String){
        this.id = id
        this.type = type
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