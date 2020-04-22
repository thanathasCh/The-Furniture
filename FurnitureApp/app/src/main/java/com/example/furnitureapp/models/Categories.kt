package com.example.furnitureapp.models

class Categories(name: String, var image: Int) {
    var name : String? = name

}

data class CategoryViewModel (
    var Id: String = "",
    var Name: String = "",
    var Description: String = "",
    var ImageUrl: String = ""
)