package com.example.furnitureapp


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.example.furnitureapp.Address.ImageModel
import com.example.furnitureapp.Categories.CategoriesFragment
import com.example.furnitureapp.data.api.AnnouncementApi
import com.example.furnitureapp.data.repository.AnnouncementRepository
import kotlinx.android.synthetic.main.fragment_home.view.*

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

//        val api = AnnouncementApi()
//        api.getAnnouncements {
//            Log.d("DEBUG", it.toString())
//        }
        val search = view.findViewById<View>(R.id.search_icon) as ImageView

        search.setOnClickListener {
            MainActivity.pageId = R.id.search_icon
            val categories =
                CategoriesFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, categories)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

//        view.fragment_home_srl.setOnRefreshListener {
//            AnnouncementRepository(this.activity).fetchAnnouncement(false) {
//                Log.d("DEBUG", it.toString())
//            }
//        }
        return view
    }
//    private fun populateList(): ArrayList<ImageModel> {
//
//        val list = ArrayList<ImageModel>()
//
//        for (i in 0..5) {
//            val imageModel = ImageModel()
//            imageModel.setImage_drawables(myImageList[i])
//            list.add(imageModel)
//        }
//
//        return list
//    }
//
//    private fun init() {
//
//        mPager = findViewById(R.id.pager) as ViewPager
//        mPager!!.adapter = SlidingImage_Adapter(this@MainActivity, this.imageModelArrayList!!)
//
//        val indicator = findViewById(R.id.indicator) as CirclePageIndicator
//
//        indicator.setViewPager(mPager)
//
//        val density = resources.displayMetrics.density
//
//        //Set circle indicator radius
//        indicator.setRadius(5 * density)
//
//        NUM_PAGES = imageModelArrayList!!.size
//
//        // Auto start of viewpager
//        val handler = Handler()
//        val Update = Runnable {
//            if (currentPage == NUM_PAGES) {
//                currentPage = 0
//            }
//            mPager!!.setCurrentItem(currentPage++, true)
//        }
//        val swipeTimer = Timer()
//        swipeTimer.schedule(object : TimerTask() {
//            override fun run() {
//                handler.post(Update)
//            }
//        }, 3000, 3000)
//
//        // Pager listener over indicator
//        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//
//            override fun onPageSelected(position: Int) {
//                currentPage = position
//
//            }
//
//            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {
//
//            }
//
//            override fun onPageScrollStateChanged(pos: Int) {
//
//            }
//        })
//
//    }
//
//    companion object {
//
//        private var mPager: ViewPager? = null
//        private var currentPage = 0
//        private var NUM_PAGES = 0
//    }
}




