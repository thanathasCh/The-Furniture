package com.example.furnitureapp

import android.view.View

interface OnItemClickListener {
    abstract fun onItemClick(position: Int, view: View?)
}