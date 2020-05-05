package com.example.furnitureapp.views.address

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.example.furnitureapp.R
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.data.repository.AddressRepository
import com.example.furnitureapp.models.Address
import com.example.furnitureapp.models.AddressViewModel
import com.example.furnitureapp.services.*
import com.example.furnitureapp.views.main.MainActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_edit_address.view.*

/**
 * A simple [Fragment] subclass.
 */
class EditAddressFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_address, container, false)
        var addressJson = arguments?.getString("id") ?: "{}"
        val isAdd = arguments?.getBoolean("add") ?: false
        val back = view.findViewById<View>(R.id.edit_back) as ImageView
        val name = view.edit_name
        val phone = view.edit_phone
        val road = view.edit_road
        val house = view.edit_address
        val subDistrict = view.edit_subdistrict
        val district = view.edit_district
        val province = view.edit_province

        val address = AddressViewModel()

        if (!isAdd) {
            val addressBuf = Gson().fromJson<AddressViewModel>(addressJson)
            name.setText(addressBuf.Name)
            phone.setText(addressBuf.TelephoneNumber)
            house.setText(addressBuf.Address)
            road.setText(addressBuf.Road)
            subDistrict.setText(addressBuf.Subdistrict)
            district.setText(addressBuf.District)
            province.setText(addressBuf.Province)
        }else{

        }

        view.edit_home.setOnClickListener {
            address.Type = 0
            view.edit_office.setBackgroundResource(R.drawable.border)
            view.edit_home.setBackgroundResource(R.drawable.grey_border)
        }

        view.edit_office.setOnClickListener {
            address.Type = 1
            view.edit_home.setBackgroundResource(R.drawable.border)
            view.edit_office.setBackgroundResource(R.drawable.grey_border)
        }

        //Save Button
        view.edit_save.setOnClickListener {
            with (address) {
                Name = name.text.toString()
                UserId = UserSharedPreference(MainActivity.mainThis).getUserId()
                TelephoneNumber = phone.text.toString()
                Address = house.text.toString()
                Road = road.text.toString()
                Subdistrict = subDistrict.text.toString()
                District = district.text.toString()
                Province = province.text.toString()
            }

            val alertBuilder = AlertBuilder()

            if (checkAddress(address)) {
                MainActivity.mainSrl.isRefreshing = true

                alertBuilder.showYesNoAlert(getString(R.string.save_confirm),
                yesOpt = {
                    if (isAdd) {
                        AddressRepository(MainActivity.mainThis).addAddress(address) {
                            if (it) {
                                alertBuilder.showOkAlert(getString(R.string.success))
                            } else {
                                alertBuilder.showOkAlert(getString(R.string.error_occurred))
                            }

                            MainActivity.mainSrl.isRefreshing = false
                            val fragmentManager = activity!!.supportFragmentManager
                            fragmentManager.popBackStack()
                        }
                    } else {
                        AddressRepository(MainActivity.mainThis).updateAddress(address) {
                            if (it) {
                                alertBuilder.showOkAlert(getString(R.string.success))
                            } else {
                                alertBuilder.showOkAlert(getString(R.string.error_occurred))
                            }

                            MainActivity.mainSrl.isRefreshing = false
                            val fragmentManager = activity!!.supportFragmentManager
                            fragmentManager.popBackStack()
                        }
                    }
                }, noOpt = {
                        MainActivity.mainSrl.isRefreshing = false
                    })
            }

        }

        //Delete Button
        view.edit_delete.setOnClickListener {
            MainActivity.mainSrl.isRefreshing = true
            val alertBuilder = AlertBuilder()
            alertBuilder.showYesNoAlert(getString(R.string.delete_confirm),
            yesOpt = {
                AddressRepository(MainActivity.mainThis).removeAddress(address.Id ?: "") {
                    if (it) {
                        alertBuilder.showOkAlert(getString(R.string.success))
                    } else {
                        alertBuilder.showOkAlert(getString(R.string.error_occurred))
                    }
                    MainActivity.mainSrl.isRefreshing = false
                    val fragmentManager = activity!!.supportFragmentManager
                    fragmentManager.popBackStack()
                }
            }, noOpt = {
                    MainActivity.mainSrl.isRefreshing = false
                })
        }

        //Back Button
        back.setOnClickListener {
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }

        return view
    }

    private fun checkAddress(address: AddressViewModel): Boolean {
        if (address.Address.isNullOrEmpty() || address.District.isNullOrEmpty() || address.Name.isNullOrEmpty() || address.Province.isNullOrEmpty()
            || address.TelephoneNumber.isNullOrEmpty() || address.Road.isNullOrEmpty() || address.Subdistrict.isNullOrEmpty() || address.Type < 0) {
            AlertBuilder().showOkAlert(getString(R.string.require_information))
            return false
        } else {
            return true
        }
    }
}
