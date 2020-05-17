package com.example.furnitureapp.views.browseItem


import android.os.Bundle
import android.util.Log
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
import com.example.furnitureapp.interfaces.PageInterface
import com.example.furnitureapp.models.ProductViewModel
import com.example.furnitureapp.services.Page
import com.example.furnitureapp.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_browse_item.view.*

/**
 * A simple [Fragment] subclass.
 */
class BrowseItemFragment : Fragment(), PageInterface,
    ClickEventHandler {
    companion object Browse {
        lateinit var browseAdapter: BrowseAdapter
    }
    var product = ArrayList<ProductViewModel>()
    lateinit var mainSrl: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initPageId(Page.PRODUCTS)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_browse_item,  container, false)
        view.alert_text.setText(" ")

        val back = view.findViewById<View>(R.id.back_btn_browse)
        back.setOnClickListener{
            val fragmentManager = activity!!.supportFragmentManager
            initPageId(Page.PRODUCT)
            fragmentManager.popBackStack()
        }

        if (MainActivity.products.size.equals(0)){
            view.alert_text.setText("Product not found!")
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
        bundle.putInt("stock", product.ProductStock)
        val productFragment = ProductFragment()
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        productFragment.arguments = bundle
        fragmentTransaction.replace(R.id.frame_layout, productFragment)
        MainActivity.productId = product.Id
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
