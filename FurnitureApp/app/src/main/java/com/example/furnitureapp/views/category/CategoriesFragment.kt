package com.example.furnitureapp.views.category


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.*
import com.example.furnitureapp.views.browseItem.BrowseItemFragment
import com.example.furnitureapp.data.api.ProductApi
import com.example.furnitureapp.data.local.CartSharedPreference
import kotlinx.android.synthetic.main.fragment_categories.view.*
import com.example.furnitureapp.data.repository.CategoryRepository
import com.example.furnitureapp.interfaces.Communicator
import com.example.furnitureapp.views.main.MainActivity


/**
 * A simple [Fragment] subclass.
 */
class CategoriesFragment : Fragment(),
    Communicator {

//    var categories = CategoriesController()
//    val adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,
//        productData)
    companion object Category {
        lateinit var categoryAdapter: CategoriesAdapter
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
//        createCategories()
        val search = view.findViewById<View>(R.id.search_btn) as ImageView
        val back = view.findViewById<View>(R.id.back) as ImageView


        back.setOnClickListener{
            MainActivity.pageId = R.id.home
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }

        search.setOnClickListener {
            var searchedProduct = view.search_bar.text.toString()
            var name:String? = null
//            for (i in productData){
////                if (searchedProduct.equals(i.name)){
////                    name = searchedProduct
////                }
////            }
            var bundle = Bundle()
            bundle.putBoolean("from search bar", true)
            bundle.putString("search",name)
            val item = BrowseItemFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            item.arguments = bundle
            fragmentTransaction.replace(R.id.frame_layout,item)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        val cartShare = CartSharedPreference(MainActivity.mainThis)
        Log.d("DEBUG", cartShare.retrieveCarts().toString())

        val categoriesView = view.findViewById<RecyclerView>(R.id.recyclerCategories) as RecyclerView
        categoriesView.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, true)

        MainActivity.mainSrl.isRefreshing = true
        CategoryRepository(MainActivity.mainThis).fetchCategory(false) {
            MainActivity.categories = it
            categoryAdapter = CategoriesAdapter(MainActivity.categories,this)
            categoriesView.adapter = categoryAdapter
            MainActivity.mainSrl.isRefreshing = false
        }

        return view

    }



    override fun clickListener(holder: View, id: String?) {
        val item = BrowseItemFragment()
        MainActivity.categoryId = id ?: ""
        MainActivity.mainSrl.isRefreshing = true
        ProductApi().getProductByCategoryId(id ?: "") {
            MainActivity.products.clear()
            MainActivity.products.addAll(it)
            BrowseItemFragment.browseAdapter.notifyDataSetChanged()
            MainActivity.mainSrl.isRefreshing = false
        }
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        MainActivity.pageId = R.layout.fragment_browse_item
        fragmentTransaction.replace(R.id.frame_layout, item)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit() //To change body of created functions use File | Settings | File Templates.
    }

    override fun clickWithDataTransfer(holder: View, id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clickToSelect(holder: View, id: String) {
        TODO("Not yet implemented")
    }
}
