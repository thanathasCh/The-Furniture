package com.example.furnitureapp.User


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.furnitureapp.*
import kotlinx.android.synthetic.main.fragment_create_new.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val create_new = view.findViewById<View>(R.id.create_new_btn)
        val login = view.findViewById<View>(R.id.login_btn)
//        val userName = view.findViewById<View>(R.id.email) as EditText
//        val password = view.findViewById<View>(R.id.password) as EditText

        create_new.setOnClickListener {
            val createnew = CreateNewFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, createnew)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        login.setOnClickListener {
            var success = false
            for (i in allUser) {
                e("input User name:", (view.login_email.text.toString() == i.UserName).toString())
                e("input password:", (view.login_password.text.toString()==i.password).toString())
                e("all user name:", i.UserName)
                e("all user password:", i.password)
                if (i.UserName!!.equals(view.login_email.text.toString()) && i.password!!.equals(view.login_password.text.toString())) {
                    isLogin = true
                    currentUser.add(i)
                    success = true
                }
            }
            if (success) {
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle("Login Success")
                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
                    val fragmentManager = activity!!.supportFragmentManager
                    fragmentManager.popBackStack()
                }
                builder.show()
            }else{
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle("Wrong User or Password")
                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
                }
                builder.show()
            }
        }



            return view
        }


    }
