package com.example.furnitureapp.models

class Catergories(name: String, var image: Int) {
    var name : String? = name

}

data class CategoryViewModel (
    var Name: String? = null,
    var Description: String? = null,
    var UmageUrl: String? = null
)