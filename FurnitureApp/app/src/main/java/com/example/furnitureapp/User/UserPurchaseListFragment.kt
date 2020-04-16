package com.example.furnitureapp.User

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.R
import com.example.furnitureapp.allUser


/**
 * A simple [Fragment] subclass.
 */
class UserPurchaseListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_user_purchase_list, container, false)
        var back = view.findViewById<View>(R.id.user_purchase_back)

        val listofPurchase = view.findViewById<RecyclerView>(R.id.recycler_view_user_purchase_list) as RecyclerView
        listofPurchase.layoutManager = LinearLayoutManager(activity,  LinearLayoutManager.VERTICAL, true)


        //Button Action
        back.setOnClickListener {
            var fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }

        //RecyclerView
        listofPurchase.adapter =
            UserPurchaseListAdapter(
                allUser[userIndex!!].productList,
                this
            )

        return view
    }

}
