package com.example.furnitureapp.services

import com.example.furnitureapp.R
import com.example.furnitureapp.models.Address
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.models.User

var userIndex: Int? = null
val addressList = arrayListOf<Address>()

val allUser = arrayListOf(User(
            "u1",
            "Male",
            "Menh",
            "Keo",
            "menhk",
            "Menhk168",
        "012-345-6789",
            "menhk@gmail.com",
    addressList,
    arrayListOf()),
    User(
        "u2",
        "Male",
        "Oat",
        "Ty",
        "oatty",
        "12345",
        "012-345-6789",
        "menhk@gmail.com",
        addressList,
        arrayListOf())
        )

val productData = arrayListOf(
    Product(
        "t1",
        "Sofa Table",
        "100 x 20 x 30",
        "A02",
        1500.0F,
        R.drawable.table_product,
        "Glass, Wood",
        4
    ),

    Product(
        "t2",
        "Desk",
        "150 x 65 x 50",
        "A01",
        2000.0F,
        R.drawable.desk_product,
        "Wood",
        2
    )
)