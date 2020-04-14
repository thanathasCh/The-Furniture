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
import com.example.furnitureapp.models.ProductViewModel

/**
 * A simple [Fragment] subclass.
 */
class BrowseItemFragment : Fragment(),
    ClickEventHandler {
    companion object Browse {
        lateinit var browseAdapter: BrowseAdapter
    }
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
            MainActivity.pageId = R.id.search_icon
            fragmentManager.popBackStack()
        }


        val listOfProduct = view.findViewById<RecyclerView>(R.id.recyclerview_list_of_product) as RecyclerView
        listOfProduct.layoutManager = LinearLayoutManager(activity,  LinearLayoutManager.VERTICAL, true)
        browseAdapter = BrowseAdapter(MainActivity.products, this)
        listOfProduct.adapter = browseAdapter
        return view
    }

    override fun forwardClick(holder: View, product: ProductViewModel) {
        val bundle = Bundle()
        bundle.putString("id", product.Id)
        bundle.putString("name", product.Name)
        bundle.putString("size", product.Description)
        bundle.putString("code", product.Code)
        bundle.putDouble("price", product.Price)
        bundle.putStringArrayList("image", product.ImageUrls)
        bundle.putString("material", product.Material)
        bundle.putBoolean("available", product.IsActive)
        val productFragment = ProductFragment()
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        productFragment.arguments = bundle
        fragmentTransaction.replace(R.id.frame_layout, productFragment)
        MainActivity.pageId = R.layout.fragment_product
        MainActivity.productId = product.Id ?: ""
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
//    override fun forwardClick(
//        holder: View,
//        id: String,
//        name: String,
//        size: String,
//        code: String,
//        price: Float,
//        image: Int,
//        material: String,
//        available: Int
//    ) {
//        val bundle = Bundle()
//        bundle.putString("id",id)
//        bundle.putString("name",name)
//        bundle.putString("size",size)
//        bundle.putString("code",code)
//        bundle.putFloat("price",price)
//        bundle.putInt("image",image)
//        bundle.putString("material", material)
//        bundle.putInt("available", available)
//        val product = ProductFragment()
//        val fragmentManager = activity!!.supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        product.arguments = bundle
//        fragmentTransaction.replace(R.id.frame_layout,product)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
//
//    }

}
