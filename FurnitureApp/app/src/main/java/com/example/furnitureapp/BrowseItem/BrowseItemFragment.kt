package com.example.furnitureapp.BrowseItem


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.Categories.CategoriesAdapter
import com.example.furnitureapp.Categories.CategoriesFragment
import com.example.furnitureapp.ClickEventHandler
import com.example.furnitureapp.ProductFragment
import com.example.furnitureapp.R

/**
 * A simple [Fragment] subclass.
 */
class BrowseItemFragment : Fragment(),
    ClickEventHandler {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_browse_item, container, false)

        val back = view.findViewById<View>(R.id.back_btn_browse)
        back.setOnClickListener{
            val categories =
                CategoriesFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,categories)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
//
////        TODO bind data in to recycler view
//        val listOfProduct = view.findViewById<RecyclerView>(R.id.recyclerview_list_of_product) as RecyclerView
//        listOfProduct.layoutManager = LinearLayoutManager(activity,  LinearLayoutManager.VERTICAL, true)
//        listOfProduct.adapter =
//            CategoriesAdapter(
//                //TODO pass the data APIs in,
//                this
//            )

        return view
    }


    override fun forwardClick(holder: View) {
        val product = ProductFragment()
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,product)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


}
