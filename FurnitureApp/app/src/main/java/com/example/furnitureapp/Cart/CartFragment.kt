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
import com.example.furnitureapp.R
import com.example.furnitureapp.models.CategoriesController
import com.example.furnitureapp.models.ProductController
import com.example.furnitureapp.models.Product

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {

    var singleton = ProductController()
    var currentKey:String = ""
    var cartProduct = ArrayList<Product>()
    var categories = CategoriesController()
//    lateinit var recyclerView: RecyclerView
//    lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //TODO
        getAllSharePref()
//        e("global: ", getSharePref("t1"))
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        val arrayKey = currentKey.split(",")
        val listOfProduct = view.findViewById<RecyclerView>(R.id.recycler_view_cart) as RecyclerView

        for (i in 0 until arrayKey.size-1){
            for (j in singleton.createMockUp()){
                if (arrayKey[i].substring(0,2).equals(j.id)){
                    if (!cartProduct.contains(j)){
                        cartProduct.add(j)
                        e("cart product is:",j.name )
                    }
//                cartProduct.distinct()

                }
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

        if (key != null) {
            for (entry in key) {
                currentKey += "$entry,"
                e("global is", currentKey)
            }
        }
    }

//    fun refreshAdapter() {
//        adapter = CartAdapter(cater, R.layout., dateList, selectIndex)
//        countryListView.adapter = adapter
//        adapter.notifyDataSetChanged()
//    }



}
