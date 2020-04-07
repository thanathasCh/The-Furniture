package com.example.furnitureapp.User

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.furnitureapp.R
import com.example.furnitureapp.addressList
import com.example.furnitureapp.allUser
import com.example.furnitureapp.userIndex
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
        var view =  inflater.inflate(R.layout.fragment_password_setting, container, false)
        var originPassword  = view.findViewById<View>(R.id.password_origin) as EditText
        var newPassword = view.findViewById<View>(R.id.password_new_password) as EditText
        var confirmPassword = view.findViewById<View>(R.id.password_confirm_password) as EditText

        // Action Button
        view.password_save.setOnClickListener {
            if (!originPassword.text.toString().equals(allUser[userIndex!!].password)){
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle("Wrong Password")
                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
                }
                builder.show()
            }else{
                if (newPassword.text.equals("") || newPassword.text.toString().length<8){
                    val builder = AlertDialog.Builder(this.activity)
                    builder.setTitle("Password Must More than 8 character!")
                    builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
                    }
                    builder.show()
                }else{
                    if (newPassword.text.toString().equals(confirmPassword.text.toString())){
                        allUser[userIndex!!].password = confirmPassword.text.toString()
                        val builder = AlertDialog.Builder(this.activity)
                        builder.setTitle("Success !")
                        builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
                            var fragmentManager = activity!!.supportFragmentManager
                            fragmentManager.popBackStack()
                        }
                        builder.show()
                    }else{
                        val builder = AlertDialog.Builder(this.activity)
                        builder.setTitle("Password Not Match")
                        builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
                        }
                        builder.show()
                    }
                }
            }
        }
        view.password_setting_back.setOnClickListener {
            var fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }




        return view
    }

}
