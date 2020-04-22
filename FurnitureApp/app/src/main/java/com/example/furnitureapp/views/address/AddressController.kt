package com.example.furnitureapp.views.address

import android.app.Application
import com.example.furnitureapp.models.Address

class AddressController : Application() {

    var AddressList = ArrayList<Address>()

    fun setAddress(address: Address){
        AddressList.add(address)
    }
    fun getAddress(): ArrayList<Address> {
        return AddressList
    }



}