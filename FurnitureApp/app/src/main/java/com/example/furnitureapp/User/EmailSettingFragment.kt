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
import com.example.furnitureapp.data.api.UserApi
import com.example.furnitureapp.isEmailValid
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
            val builder = AlertDialog.Builder(this.activity)
            if (isEmailValid(newEmail.text.toString())) {
                UserApi().updateEmail(newEmail.text.toString()) {
                    if (it) {
                        with(builder) {
                            setTitle("Success!")
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
            } else {
                builder.setTitle("Email format is incorrect")
                builder.setPositiveButton("Okay") { _: DialogInterface?, _: Int -> }
            }

            builder.show()
        }

        view.email_setting_back.setOnClickListener {
            val fragment = activity!!.supportFragmentManager
            fragment.popBackStack()
        }

        return view
    }
}
