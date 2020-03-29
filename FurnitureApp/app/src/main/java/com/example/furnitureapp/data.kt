package com.example.furnitureapp

import com.example.furnitureapp.models.Address
import com.example.furnitureapp.models.Categories
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.models.User

var isLogin = false
val currentUser = arrayListOf<User>()

val categoriesData = arrayListOf(
    Categories(
        "Table",
        R.drawable.desk
    ),
    Categories(
        "Chair",
        R.drawable.chair
    ),
    Categories(
        "Matress",
        R.drawable.mattress
    ),
    Categories(
        "Closet",
        R.drawable.closet
    ),
    Categories(
        "Bed",
        R.drawable.bed
    ),
    Categories(
        "More",
        R.drawable.more
    )
)
val purchaseList = arrayListOf<Product>()

val allUser: ArrayList<User>
    get() = arrayListOf(User(
        "u1",
        "Menh",
        "Keo",
        "menhk",
        "Menhk168",
        "menhk@gmail.com",
        addressData,
        purchaseList)
    )

val addressData = arrayListOf<Address>(
    Address(
        "a1",
        "Home",
        "Xell",
        "Road 123",
        "House 456",
        "Bang Bo",
        "Bang Bo",
        "Samut Prakarn",
        true,
        "012-345-6789"
    ),
    Address(
        "a2",
        "Office",
        "Menh",
        "Road 2004",
        "House 576",
        "Thong Lor",
        "Thong Lor",
        "Bangkok",
        false,
        "098-765-4321"
    )
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

