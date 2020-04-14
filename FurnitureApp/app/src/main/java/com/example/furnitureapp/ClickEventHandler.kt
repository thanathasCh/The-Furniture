package com.example.furnitureapp

import android.view.View
import com.example.furnitureapp.models.ProductViewModel

interface ClickEventHandler {
    fun forwardClick(holder: View, product: ProductViewModel)
}