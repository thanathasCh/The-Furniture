package com.example.furnitureapp.views.user

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.ScrollView
import com.example.furnitureapp.*
import com.example.furnitureapp.data.api.UserApi
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.services.AlertBuilder
import com.example.furnitureapp.services.encrypt
import com.example.furnitureapp.services.hideKeyboard
import com.example.furnitureapp.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_password_setting.view.*
import kotlinx.android.synthetic.main.ok_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class PasswordSettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_password_setting, container, false)
        val originPasswordEditText = view.findViewById<View>(R.id.password_origin) as EditText
        val newPasswordEditText = view.findViewById<View>(R.id.password_new_password) as EditText
        val confirmPasswordEditText =
            view.findViewById<View>(R.id.password_confirm_password) as EditText
        val passwordFragment  = view.findViewById<View>(R.id.password_fragement) as RelativeLayout

        passwordFragment.setOnClickListener {
            view.hideKeyboard()
        }

        // Action Button
        view.password_save.setOnClickListener {
            val user = UserSharedPreference(MainActivity.mainThis).retrieveUser()
            val originPassword = originPasswordEditText.text.toString().encrypt()
            val newPassword = newPasswordEditText.text.toString().encrypt()
            val confirmPassword = confirmPasswordEditText.text.toString().encrypt()
            val builder = AlertBuilder()

            if (originPassword != user.Password) {
                builder.showOkAlert(
                    getString(R.string.password_invalid),
                    getString(R.string.wrong_password)
                )
            } else {
                if (newPassword.isEmpty() || newPassword.length < 8) {
                    builder.showOkAlert(
                        getString(R.string.password_invalid),
                        getString(R.string.length_not_enough)
                    )

                } else {
                    if (newPassword == confirmPassword) {
                        val fragmentManager = activity!!.supportFragmentManager
                        UserApi().updatePassword(newPassword) {
                            if (it) {
                                builder.showOkAlertWithAction(
                                    getString(R.string.pw_update),
                                    getString(R.string.success)
                                ).ok_btn.setOnClickListener {
                                    fragmentManager.popBackStack()
                                    builder.dismiss()
                                }
                            } else {
                                builder.showOkAlert(
                                    getString(R.string.password),
                                    getString(R.string.error_occurred)
                                )
                            }
                        }
                    } else {
                        builder.showOkAlert(
                            getString(R.string.password_invalid),
                            getString(R.string.pw_notmatch)
                        )

                    }
                }

            }
        }
        view.password_setting_back.setOnClickListener {
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }




        return view
    }

}
