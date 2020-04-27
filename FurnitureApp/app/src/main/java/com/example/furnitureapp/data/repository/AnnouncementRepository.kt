package com.example.furnitureapp.data.repository

import android.content.Context
import com.example.furnitureapp.data.api.AnnouncementApi
import com.example.furnitureapp.data.local.AnnouncementSharedPreference
import com.example.furnitureapp.models.AnnouncementViewModel

class AnnouncementRepository(private val context: Context) {
    //Get Announcement and save in Shared Preferences
    fun fetchAnnouncement(isRemotePreferred: Boolean, callback: (ArrayList<AnnouncementViewModel>) -> Unit) {
        if (isRemotePreferred) {
            requestAnnouncementApi(callback)
        } else {
            if (isExisted()) {
                callback(AnnouncementSharedPreference(context).retrieveAnnouncements())
            } else {
                requestAnnouncementApi(callback)
            }
        }
    }

    private fun requestAnnouncementApi(callback: (ArrayList<AnnouncementViewModel>) -> Unit) {
        val announcementApi = AnnouncementApi()
        announcementApi.getAnnouncements {
            saveAnnouncement(it)
            callback(it)
        }
    }

    private fun isExisted(): Boolean {
        return context.getSharedPreferences(
            "Announcement",
            Context.MODE_PRIVATE).getString("AllAnnouncement", "")!!.isNotBlank()
    }

    private fun saveAnnouncement(announcements: ArrayList<AnnouncementViewModel>) {
        AnnouncementSharedPreference(context).saveAnnouncements(announcements)
    }
}