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
import com.example.furnitureapp.*
import com.example.furnitureapp.data.api.UserApi
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.services.AlertBuilder
import com.example.furnitureapp.services.encrypt
import com.example.furnitureapp.services.hideKeyboard
import com.example.furnitureapp.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_user_name.view.*
import kotlinx.android.synthetic.main.ok_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class UserNameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_user_name, container, false)
        var firstName = view.findViewById<View>(R.id.user_name_firstname) as EditText
        var lastName = view.findViewById<View>(R.id.user_name_lastname) as EditText
        var password = view.findViewById<View>(R.id.user_name_password) as EditText
        val userNameFragment = view.findViewById<View>(R.id.user_fragment) as RelativeLayout

        userNameFragment.setOnClickListener {
            view.hideKeyboard()
        }

        view.user_name_back.setOnClickListener {
            var fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }
        view.user_name_save.setOnClickListener {
            val user = UserSharedPreference(MainActivity.mainThis).retrieveUser()
            val builder = AlertBuilder()
            if (firstName.text.toString().isEmpty() || lastName.text.toString().isEmpty()) {
                builder.showOkAlert(getString(R.string.update_name),getString(R.string.name_require))

            }else{
                if (password.text.toString().encrypt() == user.Password) {
                    UserApi().updateName(firstName.text.toString(), lastName.text.toString()) {
                        if (it) {
                            builder.showOkAlertWithAction(getString(R.string.update_name),getString(R.string.success)).ok_btn.setOnClickListener {
                                val fragment = activity!!.supportFragmentManager
                                fragment.popBackStack()
                                builder.dismiss()
                            }

                        } else {
                            builder.showOkAlert(getString(R.string.update_name),getString(R.string.error_occurred))

                        }
                    }
                }else {
                    builder.showOkAlert(getString(R.string.password_invalid),getString(R.string.wrong_password))

                }
            }

        }
        return view



    }

}
