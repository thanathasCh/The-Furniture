package com.example.furnitureapp.BrowseItem


import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.*
import com.example.furnitureapp.Categories.CategoriesFragment
import com.example.furnitureapp.Product.ProductFragment
import com.example.furnitureapp.models.CategoriesController
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.models.ProductController

/**
 * A simple [Fragment] subclass.
 */
class BrowseItemFragment : Fragment(),
    ClickEventHandler {

    var product = ArrayList<Product>()
    var singleton = ProductController()
    var categories = CategoriesController()
    


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_browse_item, container, false)

        val back = view.findViewById<View>(R.id.back_btn_browse)
        back.setOnClickListener{
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }


////        TODO bind data in to recycler view
        val listOfProduct = view.findViewById<RecyclerView>(R.id.recyclerview_list_of_product) as RecyclerView
        listOfProduct.layoutManager = LinearLayoutManager(activity,  LinearLayoutManager.VERTICAL, true)
        listOfProduct.adapter =
            BrowseAdapter(
                productData,
                this
            )

        return view
    }


    override fun forwardClick(
        holder: View,
        id: String,
        name: String,
        size: String,
        code: String,
        price: Float,
        image: Int,
        material: String,
        available: Int
    ) {
        val bundle = Bundle()
        bundle.putString("id",id)
        bundle.putString("name",name)
        bundle.putString("size",size)
        bundle.putString("code",code)
        bundle.putFloat("price",price)
        bundle.putInt("image",image)
        bundle.putString("material", material)
        bundle.putInt("available", available)
        val product = ProductFragment()
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        product.arguments = bundle
        fragmentTransaction.replace(R.id.frame_layout,product)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

}
