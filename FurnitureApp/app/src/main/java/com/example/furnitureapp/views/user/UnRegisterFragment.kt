package com.example.furnitureapp.views.user


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.furnitureapp.R

/**
 * A simple [Fragment] subclass.
 */
class UnRegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_un_register, container, false)

        val login_create = view.findViewById<View>(R.id.login_or_register)

        login_create.setOnClickListener{
            var bundle = Bundle()
            bundle.putBoolean("from user", true)
            val login = LoginFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            login.arguments = bundle
            fragmentTransaction.replace(R.id.frame_layout,login)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }


}
