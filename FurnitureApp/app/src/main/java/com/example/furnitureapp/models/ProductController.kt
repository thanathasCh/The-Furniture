package com.example.furnitureapp.models

import android.app.Application
import com.example.furnitureapp.R

class ProductController : Application() {
        var productList = ArrayList<Product>()

        fun createMockUp(): ArrayList<Product> {
                productList.add(Product(
                        "t1",
                        "Sofa Table",
                        "100 x 20 x 30",
                        "A02",
                        1500.0F,
                    R.drawable.table_product,
                        "Glass, Wood",
                        4
                ))
                productList.add(Product(
                        "t2",
                        "Desk",
                        "150 x 65 x 50",
                        "A01",
                        2000.0F,
                    R.drawable.desk_product,
                        "Wood",
                        2
                ))
                return productList
        }


}