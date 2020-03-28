package com.example.furnitureapp.models

import com.google.firebase.firestore.DocumentReference
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
    var Id: String? = null,
    var CategoryId: DocumentReference? = null,
    var Name: String? = null,
    var Code: String? = null,
    var Price: Double = 0.0,
    var Description: String? = null,
    var ImageUrls: ArrayList<String> = arrayListOf(),
    var ProductStock: Int = 0,
    var StoreId: DocumentReference? = null,
    var UpdatedAt: Date? = null,
    var UpdatedBy: DocumentReference? = null,
    var CreatedAt: Date? = null,
    var CreatedBy: DocumentReference? = null,
    var IsActive: Boolean = true
)