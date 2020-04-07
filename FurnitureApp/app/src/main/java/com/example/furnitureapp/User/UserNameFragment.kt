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
import com.example.furnitureapp.R
import com.example.furnitureapp.allUser
import com.example.furnitureapp.userIndex
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
            if (firstName.text.toString().length<3 || lastName.text.toString().length<3){
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle("Name is too Short")
                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->  }
                builder.show()
            }else{
                if (password.text.toString().equals(allUser[userIndex!!].password)){
                    allUser[userIndex!!].lastName = lastName.text.toString()
                    allUser[userIndex!!].firstName = firstName.text.toString()
                    val builder = AlertDialog.Builder(this.activity)
                    builder.setTitle("Success !")
                    builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
                        var fragmentManager = activity!!.supportFragmentManager
                        fragmentManager.popBackStack()
                    }

                    builder.show()
                }else{
                    val builder = AlertDialog.Builder(this.activity)
                    builder.setTitle("Password is Incorrect")
                    builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->  }
                    builder.show()
                }
            }
        }
        return view



    }

}
