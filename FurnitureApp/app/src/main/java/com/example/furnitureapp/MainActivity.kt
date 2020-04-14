package com.example.furnitureapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.example.furnitureapp.Cart.CartFragment
import com.example.furnitureapp.Categories.CategoriesFragment
import com.example.furnitureapp.User.UnRegisterFragment
import com.example.furnitureapp.User.UserFragment
import com.example.furnitureapp.data.repository.AnnouncementRepository
import com.example.furnitureapp.data.repository.CategoryRepository
//import com.example.furnitureapp.data.api.Examples
import com.example.furnitureapp.models.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var cartFragment: CartFragment
    lateinit var userFragment: UserFragment

    var categories = CategoriesController()

    companion object Page {
        var pageId = R.id.home
        lateinit var mainThis: Context
        var categories = arrayListOf<CategoryViewModel>()
        var products = arrayListOf<ProductViewModel>()
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
                    if (isLogin) {
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
                    AnnouncementRepository(this).fetchAnnouncement(true) {
                        Log.d("DEBUG", it.toString())
                    }
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
                    main_srl.isRefreshing = false
                }
                else -> {
                    main_srl.isRefreshing = false
                }
            }
        }
    }

    override fun onBackPressed() {
        if (pageId == R.id.search_icon) {
            pageId = R.id.home
            super.onBackPressed()
        }
    }

}
