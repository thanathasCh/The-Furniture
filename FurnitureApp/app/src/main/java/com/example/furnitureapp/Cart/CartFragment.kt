package com.example.furnitureapp.Cart


import android.content.Context
import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.BrowseItem.BrowseAdapter
import com.example.furnitureapp.R
import com.example.furnitureapp.Singleton
import com.example.furnitureapp.models.Product

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {

    var singleton = Singleton()
    var currentKey:String = ""
    var cartProduct = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //TODO
        getAllSharePref()
//        e("global: ", getSharePref("t1"))
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        val listOfProduct = view.findViewById<RecyclerView>(R.id.recycler_view_cart) as RecyclerView
        for (i in singleton.createMockUp()){
            if (currentKey.substring(5,7).equals(i.id)){
                cartProduct.add(i)
//                cartProduct.distinct()
                e("cart product is:", cartProduct[0].name)
            }
        }
        listOfProduct.layoutManager = LinearLayoutManager(activity,  LinearLayoutManager.VERTICAL, true)
        listOfProduct.adapter =
            CartAdapter(
                cartProduct,
                this
            )
        return view
    }
    fun getSharePref(name: String): String {
        val sharedPref = this.activity?.getSharedPreferences("VanBooking", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.apply()
        return sharedPref?.getString(name, null).toString()
    }
    fun getAllSharePref() {
        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
        var key = sharedPref?.all
        e("work","yes")
        if (key != null) {
            for (entry in key) {
                currentKey = "$key,"
                e("global is", currentKey)
            }
        }


    }



}
