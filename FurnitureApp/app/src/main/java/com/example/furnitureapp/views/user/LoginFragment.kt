package com.example.furnitureapp.views.user


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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furnitureapp.*
import com.example.furnitureapp.data.api.UserApi
import com.example.furnitureapp.data.local.CartSharedPreference
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.data.repository.CartRepository
import com.example.furnitureapp.services.AlertBuilder
import com.example.furnitureapp.services.encrypt
import com.example.furnitureapp.services.hideKeyboard
import com.example.furnitureapp.views.cart.CartAdapter
import com.example.furnitureapp.views.cart.CartFragment
import com.example.furnitureapp.views.main.HomeFragment
import com.example.furnitureapp.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.ok_dialog.*

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

        isFromUser = if (arguments?.getBoolean("from user") != null) {
            arguments?.getBoolean("from user")!!
        } else {
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
            val alertBuilder = AlertBuilder()
            var cartShare = CartSharedPreference(MainActivity.mainThis)
            MainActivity.mainSrl.isRefreshing = true

            UserApi().isExist(
                view.login_email.text.toString(),
                view.login_password.text.toString().encrypt()
            ) {
                if (it) {
                    UserSharedPreference(MainActivity.mainThis).logIn()

                    if (isFromUser) {

                        alertBuilder.showOkAlertWithAction("Login",getString(R.string.login_successful)).ok_btn.setOnClickListener{
                            CartRepository(MainActivity.mainThis).fetchCartByUserId(true) {
                                if (cartShare.retrieveCarts().size != 0) {
                                    for (i in cartShare.retrieveCarts()) {
                                        CartRepository(MainActivity.mainThis).addCart(i.ProductId) {
                                            e("success ?:", it.toString())
                                        }
                                    }
                                }
                                MainActivity.mainSrl.isRefreshing = false
                                alertBuilder.dismiss()
                            }
                            val home = HomeFragment()
                            val fragmentManager = activity!!.supportFragmentManager
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.frame_layout, home)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        }
                    } else {
                        alertBuilder.showOkAlertWithAction("Login",getString(R.string.login_successful)).ok_btn.setOnClickListener {
                            CartRepository(MainActivity.mainThis).fetchCartByUserId(true) {
                                if (cartShare.retrieveCarts().size != 0) {
                                    for (i in cartShare.retrieveCarts()) {
                                        CartRepository(MainActivity.mainThis).addCart(i.ProductId) {
                                            e("success ?:", it.toString())
                                        }
                                    }
                                }
                                MainActivity.mainSrl.isRefreshing = false
                                alertBuilder.dismiss()
                            }
                            val fragment = activity!!.supportFragmentManager
                            fragment.popBackStack()
                        }
                    }
                } else {
                    alertBuilder.showOkAlert("Login",getString(R.string.login_fail))
                    alertBuilder.dismiss()
                }

                MainActivity.mainSrl.isRefreshing = false
            }
        }

        return view
    }
}
