package com.example.furnitureapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.furnitureapp.BrowseItem.BrowseItemFragment
import com.example.furnitureapp.Cart.CartFragment
import com.example.furnitureapp.Categories.CategoriesFragment
import com.example.furnitureapp.User.UnRegisterFragment
import com.example.furnitureapp.User.UserFragment
import com.example.furnitureapp.data.api.AnnouncementApi
import com.example.furnitureapp.data.api.ProductApi
import com.example.furnitureapp.data.api.UserApi
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.data.repository.AnnouncementRepository
import com.example.furnitureapp.data.repository.CategoryRepository
//import com.example.furnitureapp.data.api.Examples
import com.example.furnitureapp.models.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var cartFragment: CartFragment
    lateinit var userFragment: UserFragment

    var categories = CategoriesController()

    companion object Page {
        lateinit var mainThis: Context
        lateinit var mainSrl: SwipeRefreshLayout
        var pageId = R.id.home
        var categories = arrayListOf<CategoryViewModel>()
        var products = arrayListOf<ProductViewModel>()
        var categoryId = ""
        var productId = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.btm_navig)
        mainThis = this
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    pageId = R.id.home
                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.cart -> {
                    pageId = R.id.cart
                    cartFragment = CartFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, cartFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.user -> {
                    pageId = 0
                    if (UserSharedPreference(mainThis).isLogged()) {
                        userFragment = UserFragment()
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, userFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                    } else {
                        var unRegisterUser = UnRegisterFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, unRegisterUser)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                    }

                }

            }
            true
        }

        main_srl.setOnRefreshListener {
            when (pageId) {
                R.id.home -> {
//                    AnnouncementRepository(this).fetchAnnouncement(true) {
//                        Log.d("DEBUG", it.toString())
//                    }
                    main_srl.isRefreshing = false
                }
                R.id.cart -> {
                    main_srl.isRefreshing = false
                }
                R.id.search_icon -> {
                    CategoryRepository(this).fetchCategory(true) {
                        MainActivity.categories.clear()
                        MainActivity.categories.addAll(it)
                        CategoriesFragment.categoryAdapter.notifyDataSetChanged()
                        main_srl.isRefreshing = false
                    }
                }
                R.layout.fragment_browse_item -> {
                    ProductApi().getProductByCategoryId(categoryId) {
                        products.clear()
                        products.addAll(it)
                        BrowseItemFragment.browseAdapter.notifyDataSetChanged()
                        main_srl.isRefreshing = false
                    }
                }
                R.layout.fragment_product -> {
                    ProductApi().getProductById(productId) {
                        Log.d("DEBUG", it.toString())
                    }
                    main_srl.isRefreshing = false
                }
                else -> {
                    main_srl.isRefreshing = false
                }
            }
        }

        mainSrl = main_srl
    }
}
