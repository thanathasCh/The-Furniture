package com.example.furnitureapp.views.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.furnitureapp.views.address.AddressFragment
import com.example.furnitureapp.views.main.MainActivity
import com.example.furnitureapp.R
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.interfaces.PageInterface
import com.example.furnitureapp.services.Page
import kotlinx.android.synthetic.main.fragment_user_setting.view.*

/**
 * A simple [Fragment] subclass.
 */
class UserSettingFragment : Fragment(), PageInterface {
    companion object UserSetting {
        lateinit var userSettingView: View
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initPageId(Page.USER)
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_user_setting, container, false)
        //Set View Info
        val user = UserSharedPreference(MainActivity.mainThis).retrieveUser()

        with(view) {
            welcome_txt.text = getString(R.string.greeting_user, user.FirstName, user.LastName)
            setting_name.text = user.getFullName()
            setting_phone.text = user.TelephoneNumber
            setting_email.text = user.Email
        }

        //Button Action
        view.img_setting_address.setOnClickListener {
            val address = AddressFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,address)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.img_setting_name.setOnClickListener {
            val userName = UserNameFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,userName)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.setting_back.setOnClickListener {
            initPageId(Page.NONE)
            val fragment = activity!!.supportFragmentManager
            fragment.popBackStack()
        }

        view.img_setting_password.setOnClickListener {
            val password = PasswordSettingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,password)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.img_setting_email.setOnClickListener {
            val email = EmailSettingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,email)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.img_setting_phone.setOnClickListener {
            val phone = PhoneNumberFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,phone)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        userSettingView = view
        return view
    }

}
