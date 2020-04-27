package com.example.furnitureapp.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.furnitureapp.data.api.AddressApi
import com.example.furnitureapp.data.local.AddressSharedPreference
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.models.AddressViewModel

class AddressRepository(private val context: Context) {
    // Get address
    fun fetchAddresses(isRemotePreferred: Boolean, callback: (ArrayList<AddressViewModel>) -> Unit) {
        val userId = UserSharedPreference(context).retrieveUser().Id ?: ""
        if (isRemotePreferred) {
            requestAddressApi(userId, callback)
        } else {
            if (isExisted()) {
                callback(AddressSharedPreference(context).retrieveAddresses())
            } else {
                requestAddressApi(userId, callback)
            }
        }
    }

    // add new address to user
    fun addAddress(address: AddressViewModel, callback: (Boolean) -> Unit) {
        AddressApi().addAddress(address) {
            if (it) {
                AddressSharedPreference(context).addAddress(address)
                callback(true)
            } else {
                callback(false)
            }
        }
    }

    // remove address from db, pass address id
    fun removeAddress(id: String, callback: (Boolean) -> Unit) {
        AddressApi().removeAddress(id) {
            if (it) {
                AddressSharedPreference(context).removeAddress(id)
                callback(true)
            } else {
                callback(false)
            }
        }
    }

    // update address, pass the whole address model with id. it will automatically look and update the database
    fun updateAddress(address: AddressViewModel, callback: (Boolean) -> Unit) {
        val addressApi = AddressApi()
        addressApi.updateAddress(address) {
            if (it) {
                addressApi.getAddressByUserId(address.UserId ?: "") { updated ->
                    AddressSharedPreference(context).saveAddresses(updated)
                    callback(true)
                }
            } else {
                callback(false)
            }
        }
    }

    private fun requestAddressApi(userId: String, callback: (ArrayList<AddressViewModel>) -> Unit) {
        AddressApi().getAddressByUserId(userId) {
            AddressSharedPreference(context).saveAddresses(it)
            callback(it)
        }
    }


    private fun isExisted(): Boolean {
        return context.getSharedPreferences(
            "Address",
            Context.MODE_PRIVATE).getString("AllAddress", "")!!.isNotBlank()
    }
}