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
import com.example.furnitureapp.isLogin
import com.example.furnitureapp.userIndex
import kotlin.math.log

/**
 * A simple [Fragment] subclass.
 */
class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        val logout = view.findViewById<View>(R.id.logout)
        val account = view.findViewById<View>(R.id.user_account) as EditText
        var purchaseList = view.findViewById<View>(R.id.user_purchase_list) as EditText
        var about = view.findViewById<View>(R.id.about) as EditText


        // Account Button
        account.setOnClickListener {
            val setting = UserSettingFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, setting)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        //Purchase List
        purchaseList.setOnClickListener {
            val purchaseList = UserPurchaseListFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, purchaseList)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }



        //Logout Button
        logout.setOnClickListener{
            val builder = AlertDialog.Builder(this.activity)
            builder.setTitle("Logout ?")
            builder.setPositiveButton("Yes") { dialogInterface: DialogInterface?, i: Int ->
                userIndex = null
                isLogin = false
                val login = LoginFragment()
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_layout, login)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
            builder.setNegativeButton("No") { dialogInterface: DialogInterface?, i: Int ->}
            builder.show()
        }

        return view
    }


}
