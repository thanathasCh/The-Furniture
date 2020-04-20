package com.example.furnitureapp.views.user

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.furnitureapp.R
import com.example.furnitureapp.data.api.UserApi
import com.example.furnitureapp.services.AlertBuilder
import com.example.furnitureapp.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_phone_number.*
import kotlinx.android.synthetic.main.fragment_phone_number.view.*
import kotlinx.android.synthetic.main.fragment_phone_number.view.verification_code

/**
 * A simple [Fragment] subclass.
 */
class PhoneNumberFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_phone_number, container, false)
        val newPhoneNumber = view.findViewById<View>(R.id.phone_setting_new_phone) as EditText
        val newVerification = view.findViewById<View>(R.id.phone_setting_password) as EditText
        val random = ((Math.random() * 9000) + 1000).toInt()

        view.verification_code.setOnClickListener {
            AlertBuilder().showOkAlert(getString(R.string.verification_code, random))
        }

        view.phone_setting_back.setOnClickListener {
            val fragment = activity!!.supportFragmentManager
            fragment.popBackStack()
        }

        view.phone_save.setOnClickListener {
            MainActivity.mainSrl.isRefreshing = true
            val alertBuilder = AlertBuilder()

            if (newPhoneNumber.text.toString().length != 10) {
                alertBuilder.showOkAlert(getString(R.string.phone_number_incorrect)) {
                    MainActivity.mainSrl.isRefreshing = false
                }
            } else {
                if (newVerification.text.toString() == random.toString()) {
                    UserApi().updateTelephoneNumber(newPhoneNumber.text.toString()) {
                        if (it) {
                            alertBuilder.showOkAlert(getString(R.string.success)) {
                                val fragment = activity!!.supportFragmentManager
                                fragment.popBackStack()
                            }
                        } else {
                            alertBuilder.showOkAlert(getString(R.string.error_occurred))
                        }
                        MainActivity.mainSrl.isRefreshing = false
                    }
                } else {
                    alertBuilder.showOkAlert(getString(R.string.incorrect_code))
                    MainActivity.mainSrl.isRefreshing = false
                }
            }
        }
        return view

    }


}
