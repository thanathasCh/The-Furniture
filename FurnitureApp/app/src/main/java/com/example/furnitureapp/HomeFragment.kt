package com.example.furnitureapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.custom.sliderimage.logic.SliderImage
import com.example.furnitureapp.Categories.CategoriesFragment

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

//    var myImageList = ArrayList(R.drawable.slide_chair,R.drawable.slide_table)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val sliderImage = activity?.let { SliderImage(it) }
        val search = view.findViewById<View>(R.id.search_icon) as ImageView
        val slider = view.findViewById<View>(R.id.home_image) as SliderImage
        val image = listOf("https://homepages.cae.wisc.edu/~ece533/images/arctichare.png","https://homepages.cae.wisc.edu/~ece533/images/airplane.png","https://homepages.cae.wisc.edu/~ece533/images/baboon.png")
        slider.setItems(image)
//        sliderImage!!.addView(slider)
        search.setOnClickListener {
            val categories =
                CategoriesFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, categories)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
        return view
    }




}




