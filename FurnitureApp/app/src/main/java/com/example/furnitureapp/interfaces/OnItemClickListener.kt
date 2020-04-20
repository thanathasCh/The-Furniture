package com.example.furnitureapp.interfaces

import android.view.View

interface OnItemClickListener {
    abstract fun onItemClick(position: Int, view: View?)
}