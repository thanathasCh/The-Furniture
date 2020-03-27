package com.example.furnitureapp.Purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.R
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.models.ProductController

/**
 * A simple [Fragment] subclass.
 */
class ConfirmPurchaseFragment : Fragment() {

    var currentPurchaseItem = ArrayList<Product>()
    var product = ProductController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_confirm_purchase, container, false)
        var back = view.findViewById<View>(R.id.con_back) as ImageView

        var id = arguments?.getString("id")

        var amount = arguments?.getInt("amount")
        findProduct(id.toString(),amount!!)
        val listOfConfirmPurchase = view.findViewById<RecyclerView>(R.id.purchase_recycler_view) as RecyclerView
        listOfConfirmPurchase.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,true)
        listOfConfirmPurchase.adapter = ConfirmPurchaseAdapter(currentPurchaseItem,this)





        back.setOnClickListener {
            val fragment = activity!!.supportFragmentManager
            fragment.popBackStack()
        }
        return view
    }
    fun findProduct(id:String,amount:Int){
        for (i in product.createMockUp()){
            if (i.id.equals(id)){
                currentPurchaseItem.add(Product(i.id.toString(),i.name.toString(),i.size.toString(),i.code.toString(),i.price,i.image,i.material.toString(),amount))
                i.available -= amount
            }
        }
    }


}
