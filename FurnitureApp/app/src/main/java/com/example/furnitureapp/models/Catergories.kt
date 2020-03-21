package com.example.furnitureapp.models

import com.google.gson.annotations.SerializedName

class Catergories(name: String, var image: Int) {
    var name : String? = name

}

data class CategoryViewModel (
    var Id: String? = null,
    var Name: String? = null,
    var Description: String? = null,
    var ImageUrl: String? = null
)