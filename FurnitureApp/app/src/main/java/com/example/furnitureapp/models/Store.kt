package com.example.furnitureapp.models

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import java.util.*

data class StoreViewModel (
    var Id: String = "",
    var Name: String = "",
    var Address: String = "",
    var Location: GeoPoint = GeoPoint(0.0, 0.0),
    var UpdatedAt: Date = Date(),
    var UpdatedBy: String = "",
    var CreatedAt: Date = Date(),
    var CreatedBy: String = "",
    var IsActive: Boolean = true
)