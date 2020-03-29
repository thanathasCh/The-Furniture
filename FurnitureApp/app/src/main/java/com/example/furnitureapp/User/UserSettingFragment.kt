package com.example.furnitureapp.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.furnitureapp.Address.AddressFragment
import com.example.furnitureapp.R
import com.example.furnitureapp.allUser
import com.example.furnitureapp.userIndex
import kotlinx.android.synthetic.main.fragment_user_setting.view.*

/**
 * A simple [Fragment] subclass.
 */
class UserSettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_user_setting, container, false)

        //Set View Info
        view.welcome_txt.text = "Hello, "+allUser[userIndex!!].firstName+" "+allUser[userIndex!!].lastName
        view.setting_name.text = allUser[userIndex!!].firstName+" "+allUser[userIndex!!].lastName
        view.setting_phone.text = allUser[userIndex!!].addressList[0].phoneNumber
        view.setting_email.text = allUser[userIndex!!].email

        //Button Action
        view.img_setting_address.setOnClickListener {
            val address = AddressFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,address)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        view.setting_back.setOnClickListener {
            val fragment = activity!!.supportFragmentManager
            fragment.popBackStack()
        }




        return view
    }

}
