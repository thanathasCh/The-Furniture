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
import com.example.furnitureapp.allUser
import com.example.furnitureapp.userIndex
import kotlinx.android.synthetic.main.fragment_email_setting.view.*

/**
 * A simple [Fragment] subclass.
 */
class EmailSettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_email_setting, container, false)
        var newEmail = view.findViewById<View>(R.id.email_setting_new_email) as EditText

        view.email_save.setOnClickListener {
            if (newEmail.text.toString().length > 14) {
                allUser[userIndex!!].email = newEmail.text.toString()
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle("Success !")
                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
                    val fragment = activity!!.supportFragmentManager
                    fragment.popBackStack()
                }
            } else {
                allUser[userIndex!!].email = newEmail.text.toString()
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle("Wrong Email")
                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->

                }
            }
        }


        view.email_setting_back.setOnClickListener {
            val fragment = activity!!.supportFragmentManager
            fragment.popBackStack()
        }

        return view
    }
}
