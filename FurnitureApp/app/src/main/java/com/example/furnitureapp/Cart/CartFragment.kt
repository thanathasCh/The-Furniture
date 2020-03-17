package com.example.furnitureapp.Cart


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.BrowseItem.BrowseAdapter
import com.example.furnitureapp.R
import com.example.furnitureapp.Singleton

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {

    var singleton = Singleton()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //TODO
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        val listOfProduct = view.findViewById<RecyclerView>(R.id.recycler_view_cart) as RecyclerView
        listOfProduct.layoutManager = LinearLayoutManager(activity,  LinearLayoutManager.VERTICAL, true)
        listOfProduct.adapter =
            CartAdapter(
                singleton.globalVar,
                this
            )
        return view
    }


}
