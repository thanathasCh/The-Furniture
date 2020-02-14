package com.example.furnitureapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.furnitureapp.Categories.CategoriesFragment

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val seach = view.findViewById<View>(R.id.search_icon) as ImageView

        seach.setOnClickListener{
            val categories =
                CategoriesFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,categories)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
        return view
    }




}
