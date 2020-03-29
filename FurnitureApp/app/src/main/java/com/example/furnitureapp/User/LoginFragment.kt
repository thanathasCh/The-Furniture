package com.example.furnitureapp.User


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
        var isFromUser = false
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        if(arguments?.getBoolean("from user") != null) {
            isFromUser = arguments?.getBoolean("from user")!!
        }else{
            isFromUser = false
        }
        e("is from ", isFromUser.toString())
        val create_new = view.findViewById<View>(R.id.create_new_btn)
        val login = view.findViewById<View>(R.id.login_btn)


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
            view.hideKeyboard()
            for (i in 0 until allUser.size) {
                if (allUser[i].UserName!!.equals(view.login_email.text.toString()) && allUser[i].password!!.equals(view.login_password.text.toString())) {
                    isLogin = true
                    userIndex = i
                    success = true
                }
            }
            if (success) {
                val builder = AlertDialog.Builder(this.activity)
                if (isFromUser!!){
                    builder.setTitle("Login Success")
                    builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
                        val home = HomeFragment()
                        val fragmentManager = activity!!.supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.frame_layout, home)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }
                    builder.show()

                }else{
                    builder.setTitle("Login Success")
                    builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
                        val fragmentManager = activity!!.supportFragmentManager
                        fragmentManager.popBackStack()
                    }
                    builder.show()
                }

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
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    }
