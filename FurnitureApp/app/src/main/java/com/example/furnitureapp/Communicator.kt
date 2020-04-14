package com.example.furnitureapp

import android.view.View

interface Communicator {
    fun clickListener(holder: View, id: String?)
    fun clickWithDataTransfer(holder: View, id:String)
    fun clickToSelect(holder:View,id:String)
}