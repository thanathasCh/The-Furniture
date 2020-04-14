package com.example.furnitureapp.data.api

import com.example.furnitureapp.models.AnnouncementViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class AnnouncementApi(val db: CollectionReference = FirebaseFirestore.getInstance().collection("Announcements")) {
    fun getAnnouncements(callback: (ArrayList<AnnouncementViewModel>) -> Unit) {
        val now = Timestamp.now()
        val announcements = ArrayList<AnnouncementViewModel>()
        db.whereGreaterThan("InvalidAt", now)
            .get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (item in it.result!!) {
                    val bufferCategory = item.toObject(AnnouncementViewModel::class.java)
                    bufferCategory.Id = item.id
                    announcements.add(bufferCategory)
                }
                callback(announcements)
            } else {
                callback(announcements)
            }
        }
    }
}
