package com.example.furnitureapp.data.local

import android.content.Context
import com.example.furnitureapp.models.AnnouncementViewModel
import com.google.gson.Gson
import  com.example.furnitureapp.fromJson

class AnnouncementSharedPreference(private val context: Context) {
    private val sharedPreferenceKey = "Announcement"
    private val sharedPreferenceAnnouncementKey = "AllAnnouncement"

    fun saveAnnouncements(announcements: ArrayList<AnnouncementViewModel>) {
        val announcementJson = Gson().toJson(announcements)
        with(context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit()) {
            putString(sharedPreferenceAnnouncementKey, announcementJson)
            apply()
        }
    }

    fun retrieveAnnouncements(): ArrayList<AnnouncementViewModel> {
        val announcementJson =
            context.getSharedPreferences(
                sharedPreferenceKey,
                Context.MODE_PRIVATE).getString(sharedPreferenceAnnouncementKey, "")

        return if (announcementJson.isNullOrEmpty()) {
            ArrayList()
        } else {
            Gson().fromJson<ArrayList<AnnouncementViewModel>>(announcementJson)
        }
    }
}