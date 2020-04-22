package com.example.furnitureapp.models

import java.util.*

data class AnnouncementViewModel (
    var Id: String = "",
    var Title: String = "",
    var Description: String = "",
    var ImageUrl: String = "",
    var ValidAt: Date = Date(),
    var InvalidAt: Date = Date()
)