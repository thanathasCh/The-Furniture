package com.example.furnitureapp.models

import java.util.*

data class AnnouncementViewModel (
    var Id: String? = null,
    var Title: String? = null,
    var Description: String? = null,
    var ImageUrl: String? = null,
    var ValidAt: Date? = null,
    var InvalidAt: Date? = null
)