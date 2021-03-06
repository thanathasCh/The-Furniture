package com.example.furnitureapp.views.category


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.R
import com.example.furnitureapp.data.api.ProductApi
import com.example.furnitureapp.data.local.CartSharedPreference
import com.example.furnitureapp.data.repository.CategoryRepository
import com.example.furnitureapp.interfaces.Communicator
import com.example.furnitureapp.interfaces.PageInterface
import com.example.furnitureapp.services.Page
import com.example.furnitureapp.services.hideKeyboard
import com.example.furnitureapp.views.browseItem.BrowseItemFragment
import com.example.furnitureapp.views.main.MainActivity


/**
 * A simple [Fragment] subclass.
 */
class CategoriesFragment : Fragment(), PageInterface,
    Communicator {

    //    var categories = CategoriesController()
//    val adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,
//        productData)
    companion object Category {
        lateinit var categoryAdapter: CategoriesAdapter
    }


    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
//        createCategories()
        val search = view.findViewById<View>(R.id.search_btn) as ImageView
        val back = view.findViewById<View>(R.id.back) as ImageView
        val categoriesRelativeLayout = view.findViewById<View>(R.id.relative_categories) as RelativeLayout
        val searchedProduct = view.findViewById<View>(R.id.search_bar) as AutoCompleteTextView
        searchedProduct.setText("")
        initPageId(Page.SEARCH)

        categoriesRelativeLayout.setOnClickListener {
            view.hideKeyboard()
        }

        searchedProduct.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchItemCickerListener(searchedProduct)
                view.hideKeyboard()
                return@OnEditorActionListener true
            }
            false
        })


        back.setOnClickListener {
            initPageId(Page.HOME)
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }

        ProductApi().getProductNames {
            val adapter = activity?.let { it1 ->
                ArrayAdapter(
                    it1, android.R.layout.simple_list_item_1, it
                )
            }
            searchedProduct.threshold = 0
            searchedProduct.setAdapter(adapter)
        }



        search.setOnClickListener {
            view.hideKeyboard()
            searchItemCickerListener(searchedProduct)
        }
        val cartShare = CartSharedPreference(MainActivity.mainThis)
        Log.d("DEBUG", cartShare.retrieveCarts().toString())

        val categoriesView =
            view.findViewById<RecyclerView>(R.id.recyclerCategories) as RecyclerView
        categoriesView.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, true)

        MainActivity.mainSrl.isRefreshing = true
        CategoryRepository(MainActivity.mainThis).fetchCategory(false) {
            MainActivity.categories = it
            categoryAdapter = CategoriesAdapter(MainActivity.categories, this)
            categoriesView.adapter = categoryAdapter
            MainActivity.mainSrl.isRefreshing = false
        }




        return view

    }


    fun searchItemCickerListener(search: EditText) {
        var name: String? = null
        var search = search.text.toString()
        MainActivity.products.clear()
        if (search.equals(" ")) {
            MainActivity.products.clear()
        } else if (search.equals("")) {
            MainActivity.products.clear()
        } else {
            ProductApi().getProducts {
                for (i in it) {
                    if (i.Name.toLowerCase().contains(search.toLowerCase())) {
                        e("product is ", i.Name)
                        MainActivity.products.add(i)
                        BrowseItemFragment.browseAdapter.notifyDataSetChanged()
                        MainActivity.mainSrl.isRefreshing = false
                    }
                }
            }
        }

        val item = BrowseItemFragment()
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, item)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
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
        fragmentTransaction.replace(R.id.frame_layout, item)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit() //To change body of created functions use File | Settings | File Templates.
    }

    override fun clickWithDataTransfer(holder: View, id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clickToSelect(holder: View, id: Int,view: View) {
        TODO("Not yet implemented")
    }
}
