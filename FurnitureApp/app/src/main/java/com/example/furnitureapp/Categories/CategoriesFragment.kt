package com.example.furnitureapp.Categories


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.*
import com.example.furnitureapp.BrowseItem.BrowseItemFragment
import com.example.furnitureapp.models.Categories
import com.example.furnitureapp.models.CategoriesController
import kotlinx.android.synthetic.main.fragment_categories.view.*
import android.widget.AdapterView


/**
 * A simple [Fragment] subclass.
 */
class CategoriesFragment : Fragment(),
    Communicator {

    var categories = CategoriesController()
//    val adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,
//        productData)


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
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }

        search.setOnClickListener {
            var searchedProduct = view.search_bar.text.toString()
            var name:String? = null
            for (i in productData){
                if (searchedProduct.equals(i.name)){
                    name = searchedProduct
                }
            }
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

        val catergoriesView = view.findViewById<RecyclerView>(R.id.recyclerCategories) as RecyclerView
        catergoriesView.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, true)
        catergoriesView.adapter = CategoriesAdapter(categoriesData,this)

        return view

    }



    override fun clickListener(holder: View) {
        val item = BrowseItemFragment()
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,item)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit() //To change body of created functions use File | Settings | File Templates.
    }

    override fun clickWithDataTransfer(holder: View, id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clickToSelect(holder: View, id: String) {
        TODO("Not yet implemented")
    }


//    fun createCategories(){
//        categories.categoriesList.clear()
//        categories.setCategories(
//            Categories(
//                "Table",
//                R.drawable.desk
//            )
//        )
//        categories.setCategories(
//            Categories(
//                "Chair",
//                R.drawable.chair
//            )
//        )
//        categories.setCategories(
//            Categories(
//                "Matress",
//                R.drawable.mattress
//            )
//        )
//        categories.setCategories(
//            Categories(
//                "Closet",
//                R.drawable.closet
//            )
//        )
//        categories.setCategories(
//            Categories(
//                "Bed",
//                R.drawable.bed
//            )
//        )
//        categories.setCategories(
//            Categories(
//                "More",
//                R.drawable.more
//            )
//        )
//    }
//




}
