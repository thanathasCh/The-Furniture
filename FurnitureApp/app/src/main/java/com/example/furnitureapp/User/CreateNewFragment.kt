package com.example.furnitureapp.User


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.furnitureapp.R

/**
 * A simple [Fragment] subclass.
 */
class CreateNewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_new, container, false)

        val back = view.findViewById<View>(R.id.back_btn)
        val back_text = view.findViewById<View>(R.id.back)

        back.setOnClickListener{
            val login = LoginFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,login)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        back_text.setOnClickListener{
            val login = LoginFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,login)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }


}
