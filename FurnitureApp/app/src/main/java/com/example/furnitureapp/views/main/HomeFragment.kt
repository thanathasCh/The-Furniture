package com.example.furnitureapp.views.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.custom.sliderimage.logic.SliderImage
import com.example.furnitureapp.R
import com.example.furnitureapp.views.category.CategoriesFragment
import com.example.furnitureapp.data.api.AnnouncementApi

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    companion object Home {
        lateinit var slider: SliderImage
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val sliderImage = activity?.let { SliderImage(it) }
        val search = view.findViewById<View>(R.id.search_icon) as ImageView
        slider = view.findViewById<View>(
            R.id.home_image
        ) as SliderImage

        MainActivity.mainSrl.isRefreshing = true
        AnnouncementApi().getAnnouncementImages {
            slider.setItems(it)
            MainActivity.mainSrl.isRefreshing = false
        }
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




