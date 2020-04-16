package com.example.furnitureapp.User

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.furnitureapp.*
import com.example.furnitureapp.data.api.UserApi
import com.example.furnitureapp.data.local.UserSharedPreference
import kotlinx.android.synthetic.main.fragment_password_setting.view.*

/**
 * A simple [Fragment] subclass.
 */
class PasswordSettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_password_setting, container, false)
        val originPasswordEditText  = view.findViewById<View>(R.id.password_origin) as EditText
        val newPasswordEditText = view.findViewById<View>(R.id.password_new_password) as EditText
        val confirmPasswordEditText = view.findViewById<View>(R.id.password_confirm_password) as EditText

        // Action Button
        view.password_save.setOnClickListener {
            val user = UserSharedPreference(MainActivity.mainThis).retrieveUser()
            val originPassword = originPasswordEditText.text.toString().encrypt()
            val newPassword = newPasswordEditText.text.toString().encrypt()
            val confirmPassword = confirmPasswordEditText.text.toString().encrypt()
            val builder = AlertDialog.Builder(this.activity)

            if (originPassword != user.Password){
                builder.setTitle("Wrong Password")
                builder.setPositiveButton("Okay") { _: DialogInterface?, _: Int -> }
            }else{
                if (newPassword.isEmpty() || newPassword.length<8){
                    with(builder) {
                        setTitle("Password Must More than 8 character!")
                        setPositiveButton("Okay") { _: DialogInterface?, _: Int -> }
                    }
                } else {
                    if (newPassword == confirmPassword){
//                        allUser[userIndex!!].password = confirmPasswordEditText.text.toString()
                        UserApi().updatePassword(newPassword) {
                            if (it) {
                                with(builder) {
                                    setTitle("Success !")
                                    setPositiveButton("Okay") { _: DialogInterface?, _: Int ->
                                        val fragmentManager = activity!!.supportFragmentManager
                                        fragmentManager.popBackStack()
                                    }
                                }
                            } else {
                                with(builder) {
                                    setTitle("Error Occurred")
                                    setPositiveButton("Okay") {_, _ ->  }
                                }
                            }
                        }
                    } else {
                        with(builder) {
                            setTitle("Password Not Match")
                            setPositiveButton("Okay") { _: DialogInterface?, _: Int -> }
                        }
                    }
                }

                builder.show()
            }
        }
        view.password_setting_back.setOnClickListener {
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }




        return view
    }

}
