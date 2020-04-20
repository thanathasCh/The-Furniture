package com.example.furnitureapp.views.browseItem


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.furnitureapp.*
import com.example.furnitureapp.views.product.ProductFragment
import com.example.furnitureapp.interfaces.ClickEventHandler
import com.example.furnitureapp.models.CategoriesController
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.models.ProductController
import com.example.furnitureapp.models.ProductViewModel
import com.example.furnitureapp.views.main.MainActivity

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
    lateinit var mainSrl: SwipeRefreshLayout

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
}
