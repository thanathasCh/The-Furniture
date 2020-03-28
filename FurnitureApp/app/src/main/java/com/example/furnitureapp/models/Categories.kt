package com.example.furnitureapp.models

class Categories(name: String, var image: Int) {
    var name : String? = name

}

data class CategoryViewModel (
    var Id: String? = null,
    var Name: String? = null,
    var Description: String? = null,
    var ImageUrl: String? = null
)