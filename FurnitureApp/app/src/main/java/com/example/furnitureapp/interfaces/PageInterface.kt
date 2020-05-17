package com.example.furnitureapp.interfaces

import com.example.furnitureapp.services.Page
import com.example.furnitureapp.views.main.MainActivity

interface PageInterface {
    fun initPageId(page: Page? = null) {
        if (page != null) {
            MainActivity.pageId = page
        } else {
            MainActivity.pageId = Page.NONE
        }
    }
}