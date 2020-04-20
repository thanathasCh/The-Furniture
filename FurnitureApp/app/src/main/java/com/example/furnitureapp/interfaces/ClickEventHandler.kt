package com.example.furnitureapp.interfaces

import android.view.View
import com.example.furnitureapp.models.ProductViewModel

interface ClickEventHandler {
    fun forwardClick(holder: View, product: ProductViewModel)
}