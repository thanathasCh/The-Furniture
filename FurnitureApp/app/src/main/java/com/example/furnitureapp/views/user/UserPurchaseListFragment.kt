package com.example.furnitureapp.views.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.R
import com.example.furnitureapp.data.api.TransactionApi
import com.example.furnitureapp.models.TransactionViewModel
import com.example.furnitureapp.services.allUser
import com.example.furnitureapp.services.userIndex


/**
 * A simple [Fragment] subclass.
 */
class UserPurchaseListFragment : Fragment() {
    companion object UserPurchase {
        val purchases = ArrayList<TransactionViewModel>()
        lateinit var userPurchaseAdapter: UserPurchaseListAdapter
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_user_purchase_list, container, false)
        val back = view.findViewById<View>(R.id.user_purchase_back)

        val listOfPurchase = view.findViewById<RecyclerView>(R.id.recycler_view_user_purchase_list) as RecyclerView
        listOfPurchase.layoutManager = LinearLayoutManager(activity,  LinearLayoutManager.VERTICAL, true)
        userPurchaseAdapter = UserPurchaseListAdapter(purchases, this)
        listOfPurchase.adapter = userPurchaseAdapter

        TransactionApi().getTransaction {
            purchases.clear()
            purchases.addAll(it)
            userPurchaseAdapter.notifyDataSetChanged()
        }

        //Button Action
        back.setOnClickListener {
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }

        return view
    }

}
