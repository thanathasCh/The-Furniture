package com.example.furnitureapp.User


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.furnitureapp.*
import com.example.furnitureapp.data.api.UserApi
import com.example.furnitureapp.data.local.UserSharedPreference
import kotlinx.android.synthetic.main.activity_main.*
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

        isFromUser = if(arguments?.getBoolean("from user") != null) {
            arguments?.getBoolean("from user")!!
        }else{
            false
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
            view.hideKeyboard()

            MainActivity.mainSrl.isRefreshing = true
            Log.d("DEBUG", view.login_password.text.toString().encrypt())
            UserApi().isExist(view.login_email.text.toString(), view.login_password.text.toString().encrypt()) {
                val builder = AlertDialog.Builder(this.activity)
                if (it) {
                    UserSharedPreference(MainActivity.mainThis).loggedIn()
                    builder.setTitle("Login Successful")

                    if (isFromUser) {
                        builder.setPositiveButton("Okay") { _: DialogInterface?, _: Int -> val home = HomeFragment()
                                val fragmentManager = activity!!.supportFragmentManager
                                val fragmentTransaction = fragmentManager.beginTransaction()
                                fragmentTransaction.replace(R.id.frame_layout, home)
                                fragmentTransaction.addToBackStack(null)
                                fragmentTransaction.commit()
                        }
                    } else {
                        builder.setPositiveButton("Okay") { _: DialogInterface?, _: Int ->
                            val fragmentManager = activity!!.supportFragmentManager
                            fragmentManager.popBackStack()
                        }
                    }
                } else {
                    builder.setTitle("Wrong User or Password")
                    builder.setPositiveButton("Okay") { _: DialogInterface?, _: Int -> }
                }
                builder.show()
                MainActivity.mainSrl.isRefreshing = false
            }
        }

            return view
        }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    }
