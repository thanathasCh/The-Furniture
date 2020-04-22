package com.example.furnitureapp.models

import com.google.firebase.database.Exclude
import java.util.*
import kotlin.collections.ArrayList


class Product{
    var id: String? = null
    var name : String? = null
    var size : String? = null
    var code : String? = null
    var price : Float = 0.0f
    var image:Int
    var material: String? = null
    var available: Int

    constructor(id:String,name:String, size:String, code:String, price:Float, image:Int, material: String, available: Int){
        this.id = id
        this.name = name
        this.size = size
        this.code = code
        this.price = price
        this.image = image
        this.material = material
        this.available = available
    }
}

data class ProductViewModel(
    var Id: String = "",
    var CategoryId: String = "",
    var Name: String = "",
    var Code: String = "",
    var Price: Double = 0.0,
    var Description: String = "",
    var Material: String = "",
    var ImageUrls: ArrayList<String> = arrayListOf(),
    var ProductStock: Int = 0,
    var StoreId: String = "",
    var UpdatedAt: Date = Date(),
    var UpdatedBy: String = "",
    var CreatedAt: Date= Date(),
    var CreatedBy: String = "",
    var IsActive: Boolean = true
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "CategoryId" to CategoryId,
            "Name" to Name,
            "Code" to Code,
            "Price" to Price,
            "Description" to Description,
            "Material" to Material,
            "ImageUrls" to ImageUrls,
            "StoreId" to StoreId
        )
    }
}