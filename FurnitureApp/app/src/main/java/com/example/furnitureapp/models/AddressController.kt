package com.example.furnitureapp.models

import android.app.Application

class AddressController : Application() {

    var AddressList = ArrayList<Address>()

    fun setAddress(address: Address){
        AddressList.add(address)
    }
    fun getAddress(): ArrayList<Address> {
        return AddressList
    }



}