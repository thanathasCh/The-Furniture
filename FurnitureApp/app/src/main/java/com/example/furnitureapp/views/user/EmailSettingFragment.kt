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
import com.example.furnitureapp.services.isEmailValid
import kotlinx.android.synthetic.main.fragment_email_setting.view.*
import kotlinx.android.synthetic.main.ok_dialog.*
import kotlinx.android.synthetic.main.yes_no_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class EmailSettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_email_setting, container, false)
        val newEmail = view.findViewById<View>(R.id.email_setting_new_email) as EditText

        view.email_save.setOnClickListener {
            val alertBuilder = AlertBuilder()

            if (isEmailValid(newEmail.text.toString())) {
                UserApi().updateEmail(newEmail.text.toString()) {
                    if (it) {
                        alertBuilder.showOkAlertWithAction("Email Updated",getString(R.string.success)).ok_btn.setOnClickListener {
                            val fragment = activity!!.supportFragmentManager
                            alertBuilder.dismiss()
                            fragment.popBackStack()
                        }
                    } else {
                        alertBuilder.showOkAlert("Email Updated",getString(R.string.error_occurred))
                        alertBuilder.dismiss()
                    }
                }
            } else {
                alertBuilder.showOkAlert("Email Updated",getString (R.string.incorrect_email_format))
                alertBuilder.dismiss()
            }
        }

        view.email_setting_back.setOnClickListener {
            val fragment = activity!!.supportFragmentManager
            fragment.popBackStack()
        }

        return view
    }
}
