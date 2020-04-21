package com.example.furnitureapp.models

import com.google.firebase.database.Exclude

class Address{
    var id: String? = null
    var uid: String? = null
    var type:String? = null
    var name: String? = null
    var road : String? = null
    var house : String? = null
    var sub_district : String? = null
    var district : String? = null
    var province: String? = null
    var isCurrentAddress: Boolean? = false
    var phoneNumber:String? = null


    constructor(id:String,uid:String,type:String,name:String, road:String, house:String, sub_district:String, district:String, provice:String,isCurrentAddress:Boolean,phoneNumber:String){
        this.id = id
        this.uid = uid
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

data class AddressViewModel (
    var Id: String = "",
    var UserId: String = "",
    var Name: String = "",
    var TelephoneNumber: String = "",
    var Address: String = "",
    var Road: String = "",
    var Subdistrict: String = "",
    var District: String = "",
    var Province: String = "",
    var Type: Int = -1
) {
    @Exclude
    fun getFullAddress(): String {
        return "${if(Type == 0) "Home" else "Work"}, $Address, $Road $District $Subdistrict, $Province"
    }

    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "UserId" to UserId,
            "Name" to Name,
            "TelephoneNumber" to TelephoneNumber,
            "Address" to Address,
            "Road" to Road,
            "SubDistrict" to Subdistrict,
            "District" to District,
            "Province" to Province,
            "Type" to Type
            )
    }
}