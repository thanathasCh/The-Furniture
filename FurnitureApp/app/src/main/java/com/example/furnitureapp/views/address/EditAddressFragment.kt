package com.example.furnitureapp.views.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.furnitureapp.R
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.data.repository.AddressRepository
import com.example.furnitureapp.models.AddressViewModel
import com.example.furnitureapp.services.*
import com.example.furnitureapp.views.main.MainActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_edit_address.view.*
import kotlinx.android.synthetic.main.yes_no_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class EditAddressFragment : Fragment() {

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
        val addressFragment = view.findViewById<View>(R.id.address_detail_fragment) as RelativeLayout

         addressFragment.setOnClickListener {
             view.hideKeyboard()
         }

        var address = AddressViewModel()

        if (!isAdd) {
            val addressBuf = Gson().fromJson<AddressViewModel>(addressJson)
            name.setText(addressBuf.Name)
            phone.setText(addressBuf.TelephoneNumber)
            house.setText(addressBuf.Address)
            road.setText(addressBuf.Road)
            subDistrict.setText(addressBuf.SubDistrict)
            district.setText(addressBuf.District)
            province.setText(addressBuf.Province)
            address = addressBuf
        }

        view.edit_home.setOnClickListener {
            address.Type = 0
            view.edit_home.setBackgroundResource(R.drawable.border)
            view.edit_office.setBackgroundResource(R.drawable.grey_border)

        }

        view.edit_office.setOnClickListener {
            address.Type = 1
            view.edit_office.setBackgroundResource(R.drawable.border)
            view.edit_home.setBackgroundResource(R.drawable.grey_border)

        }

        //Save Button
        view.edit_save.setOnClickListener {
            with(address) {
                Name = name.text.toString()
                UserId = UserSharedPreference(MainActivity.mainThis).getUserId()
                TelephoneNumber = phone.text.toString()
                Address = house.text.toString()
                Road = road.text.toString()
                SubDistrict = subDistrict.text.toString()
                District = district.text.toString()
                Province = province.text.toString()
            }

            if (checkAddress(address)) {
                MainActivity.mainSrl.isRefreshing = true
                val alertBuilder = AlertBuilder()
                alertBuilder.showYesNoAlert(
                    getString(R.string.save_address),
                    getString(R.string.save_confirm)
                ).yes_btn.setOnClickListener {
                    if (isAdd) {
                        AddressRepository(MainActivity.mainThis).addAddress(address) {
                            if (it) {
                                alertBuilder.showOkAlert(
                                    getString(R.string.save_address),
                                    getString(R.string.success)
                                )
                                alertBuilder.dismiss()
                            } else {
                                alertBuilder.showOkAlert(
                                    getString(R.string.save_address_fail),
                                    getString(R.string.error_occurred)
                                )
                                alertBuilder.dismiss()
                            }

                            MainActivity.mainSrl.isRefreshing = false
                            val fragmentManager = activity!!.supportFragmentManager
                            fragmentManager.popBackStack()
                        }
                    } else {
                        AddressRepository(MainActivity.mainThis).updateAddress(address) {
                            if (it) {
                                alertBuilder.showOkAlert(
                                    getString(R.string.update_address),
                                    getString(R.string.success)
                                )
                                alertBuilder.dismiss()
                            } else {
                                alertBuilder.showOkAlert(
                                    getString(R.string.update_address_fail),
                                    getString(R.string.error_occurred)
                                )
                                alertBuilder.dismiss()
                            }

                            MainActivity.mainSrl.isRefreshing = false
                            val fragmentManager = activity!!.supportFragmentManager
                            fragmentManager.popBackStack()
                        }
                    }
                }

                val alertBuilderWithAction = alertBuilder.showYesNoAlertWithAction(
                    getString(R.string.save_address),
                    getString(R.string.save_confirm)
                )


                alertBuilderWithAction.yes_btn.setOnClickListener {
                    if (isAdd) {
                        AddressRepository(MainActivity.mainThis).addAddress(address) {
                            if (it) {
                                alertBuilder.showOkAlert(
                                    getString(R.string.save_address),
                                    getString(R.string.success)
                                )
                                alertBuilder.dismiss()
                            } else {
                                alertBuilder.showOkAlert(
                                    getString(R.string.save_address_fail),
                                    getString(R.string.error_occurred)
                                )
                                alertBuilder.dismiss()
                            }

                            MainActivity.mainSrl.isRefreshing = false
                            val fragmentManager = activity!!.supportFragmentManager
                            fragmentManager.popBackStack()
                        }
                    } else {
                        AddressRepository(MainActivity.mainThis).updateAddress(address) {
                            if (it) {
                                alertBuilder.showOkAlert(
                                    getString(R.string.update_address),
                                    getString(R.string.success)
                                )
                                alertBuilder.dismiss()
                            } else {
                                alertBuilder.showOkAlert(
                                    getString(R.string.update_address_fail),
                                    getString(R.string.error_occurred)
                                )
                                alertBuilder.dismiss()
                            }

                            MainActivity.mainSrl.isRefreshing = false
                            val fragmentManager = activity!!.supportFragmentManager
                            fragmentManager.popBackStack()
                        }
                    }
                }
                alertBuilderWithAction.no_btn.setOnClickListener {
                    MainActivity.mainSrl.isRefreshing = false
                }
            }
        }

        //Delete Button
        view.edit_delete.setOnClickListener {
            MainActivity.mainSrl.isRefreshing = true
            val alertBuilder = AlertBuilder()
            val alertBuilderWithAction = alertBuilder.showYesNoAlertWithAction(
                getString(R.string.delete_address),
                getString(R.string.delete_confirm)
            )

            alertBuilderWithAction.yes_btn.setOnClickListener {
                AddressRepository(MainActivity.mainThis).removeAddress(address.Id) {
                    if (it) {
                        alertBuilder.showOkAlert(getString(R.string.delete_address), getString(R.string.success))
                        alertBuilder.dismiss()
                    } else {
                        alertBuilder.showOkAlert(
                            getString(R.string.delete_fail),
                            getString(R.string.error_occurred)
                        )
                        alertBuilder.dismiss()
                    }
                    MainActivity.mainSrl.isRefreshing = false
                    val fragmentManager = activity!!.supportFragmentManager
                    fragmentManager.popBackStack()
                }
            }
            val alertBuilderWithYesNo = alertBuilder.showYesNoAlertWithAction(
                getString(R.string.con_delete),
                getString(R.string.delete_confirm)
            )
            alertBuilderWithYesNo.dismiss()
            alertBuilderWithYesNo.yes_btn.setOnClickListener {
                AddressRepository(MainActivity.mainThis).removeAddress(address.Id) {
                    if (it) {
                        alertBuilder.showOkAlert(getString(R.string.delete_address),getString(R.string.success))
                        alertBuilder.dismiss()
                    } else {
                        alertBuilder.showOkAlert(getString(R.string.delete_address),getString(R.string.error_occurred))
                        alertBuilder.dismiss()
                    }
                    MainActivity.mainSrl.isRefreshing = false
                    val fragmentManager = activity!!.supportFragmentManager
                    fragmentManager.popBackStack()
                }
            }
            alertBuilderWithYesNo.no_btn.setOnClickListener {
                MainActivity.mainSrl.isRefreshing = false
            }
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
            || address.TelephoneNumber.isNullOrEmpty() || address.Road.isNullOrEmpty() || address.SubDistrict.isNullOrEmpty() || address.Type < 0
        ) {
            AlertBuilder().showOkAlert(
                getString(R.string.info_invalid),
                getString(R.string.require_information)
            )
            AlertBuilder().dismiss()
            return false
        } else {
            return true
        }
    }
}
