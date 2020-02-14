package com.example.furnitureapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.furnitureapp.BrowseItem.BrowseItemFragment
import com.example.furnitureapp.Categories.CategoriesFragment

/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment() {

// TODO set all the detail according to each id name


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_product, container, false)

        val back = view.findViewById<View>(R.id.back_product_btn)

        back.setOnClickListener{
            val browse =
                BrowseItemFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,browse)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        return view
    }


}
