package com.example.furnitureapp.User

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.furnitureapp.*
import com.example.furnitureapp.data.api.UserApi
import com.example.furnitureapp.data.local.UserSharedPreference
import kotlinx.android.synthetic.main.fragment_create_new.view.*
import kotlinx.android.synthetic.main.fragment_user_name.view.*

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

        view.user_name_back.setOnClickListener {
            var fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }
        view.user_name_save.setOnClickListener {
            val user = UserSharedPreference(MainActivity.mainThis).retrieveUser()
            val builder = AlertDialog.Builder(this.activity)
            if (firstName.text.toString().isEmpty() || lastName.text.toString().isEmpty()) {
                builder.setTitle("Name and LastName are required!")
                builder.setPositiveButton("Okay") { _: DialogInterface?, _: Int ->  }
            }else{
                if (password.text.toString().encrypt() == user.Password) {
                    UserApi().updateName(firstName.text.toString(), lastName.text.toString()) {
                        if (it) {
                            with(builder) {
                                setTitle("Success !")
                                setPositiveButton("Okay") { _, _ ->
                                    val fragment = activity!!.supportFragmentManager
                                    fragment.popBackStack()
                                }
                            }
                        } else {
                            with(builder) {
                                setTitle("Error Occurred")
                                setPositiveButton("Okay") { _, _ ->  }
                            }
                        }
                    }
                }else {
                    builder.setTitle("Password is Incorrect")
                    builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->  }
                }
            }
            builder.show()
        }
        return view



    }

}
