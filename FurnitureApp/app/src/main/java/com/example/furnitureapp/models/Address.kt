package com.example.furnitureapp.models

import com.google.firebase.database.Exclude

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