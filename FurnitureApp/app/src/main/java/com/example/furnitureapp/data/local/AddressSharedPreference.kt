package com.example.furnitureapp.data.local

import android.content.Context
import com.example.furnitureapp.models.AddressViewModel
import com.example.furnitureapp.services.fromJson
import com.google.gson.Gson

class AddressSharedPreference(private val context: Context) {
    private val sharedPreferenceKey = "Address"
    private val sharedPreferenceAddressKey = "AllAddress"

    fun saveAddresses(addresses: ArrayList<AddressViewModel>) {
        val addressJson = Gson().toJson(addresses)

        with (context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE).edit()) {
            putString(sharedPreferenceAddressKey, addressJson)
            apply()
        }
    }

    fun retrieveAddresses(): ArrayList<AddressViewModel> {
        val addressJson = context.getSharedPreferences(
            sharedPreferenceKey,
            Context.MODE_PRIVATE
        ).getString(sharedPreferenceAddressKey, "")

        return if (addressJson.isNullOrEmpty()) {
            ArrayList()
        } else {
            Gson().fromJson<ArrayList<AddressViewModel>>(addressJson)
        }
    }

    fun removeAddress(id: String) {
        val addressJson = context.getSharedPreferences(
            sharedPreferenceKey,
            Context.MODE_PRIVATE
        ).getString(sharedPreferenceAddressKey, "")

        val addresses = Gson().fromJson<ArrayList<AddressViewModel>>(addressJson ?: "")

        for (i in 0 until addresses.size) {
            if (addresses[i].Id == id) {
                addresses.removeAt(i)
                break
            }
        }

        saveAddresses(addresses)
    }

    fun addAddress(address: AddressViewModel) {
        val addressJson = context.getSharedPreferences(
            sharedPreferenceKey,
            Context.MODE_PRIVATE
        ).getString(sharedPreferenceAddressKey, "")

        val addresses = Gson().fromJson<ArrayList<AddressViewModel>>(addressJson ?: "")
        addresses.add(address)
        saveAddresses(addresses)
    }
}