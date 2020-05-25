package com.example.furnitureapp.storeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.custom.sliderimage.logic.SliderImage

import com.example.furnitureapp.R
import com.example.furnitureapp.views.main.HomeFragment

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val slider:SliderImage
        // Inflate the layout for this fragment
       var view = inflater.inflate(R.layout.fragment_about, container, false)
        val back = view.findViewById<View>(R.id.about_back)

        slider = view.findViewById<View>(
            R.id.about_image
        ) as SliderImage
        val storeLocation = ArrayList<String>()
        storeLocation.add("https://www.ikea.com/images/ikea-bangna-e2d2063e5cb3a221c34cc1fc90b91804.jpg?f=xxxl")
        storeLocation.add("https://www.thaiticketmajor.com/bus/imgUpload/Image/mega-bangna-map.jpg")
        slider.setItems(storeLocation)
        back.setOnClickListener {
            val fragment = activity!!.supportFragmentManager
            fragment.popBackStack()
        }





        return view

    }

}
