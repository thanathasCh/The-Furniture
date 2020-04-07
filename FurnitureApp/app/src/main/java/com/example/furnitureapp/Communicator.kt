package com.example.furnitureapp

import android.view.View
import android.widget.EditText

interface Communicator {
    fun clickListener(holder: View)
    fun clickWithDataTransfer(holder: View, id:String)
    fun clickToSelect(holder:View,id:String)
}