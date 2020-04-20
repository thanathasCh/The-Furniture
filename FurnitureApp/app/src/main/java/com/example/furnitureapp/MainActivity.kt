package com.example.furnitureapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.furnitureapp.views.browseItem.BrowseItemFragment
import com.example.furnitureapp.views.cart.CartFragment
import com.example.furnitureapp.views.category.CategoriesFragment
import com.example.furnitureapp.views.product.ProductFragment
import com.example.furnitureapp.views.user.UnRegisterFragment
import com.example.furnitureapp.views.user.UserFragment
import com.example.furnitureapp.data.api.AnnouncementApi
import com.example.furnitureapp.data.api.ProductApi
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.data.repository.CategoryRepository
//import com.example.furnitureapp.data.api.Examples
import com.example.furnitureapp.models.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product.view.*

class MainActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var cartFragment: CartFragment
    lateinit var userFragment: UserFragment

    companion object Page {
        lateinit var mainThis: Context
        lateinit var mainSrl: SwipeRefreshLayout
        var pageId = R.id.home
        var categories = arrayListOf<CategoryViewModel>()
        var products = arrayListOf<ProductViewModel>()
        var categoryId = ""
        var productId = ""
    }

    @SuppressLint("ResourceAsColor")
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
                    if (UserSharedPreference(mainThis).isLogin()) {
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
            main_srl.setColorSchemeColors(android.R.color.holo_blue_bright,android.R.color.holo_green_light,android.R.color.holo_orange_light)
            when (pageId) {
                R.id.home -> {
                    AnnouncementApi().getAnnouncementImages {
                        HomeFragment.slider.setItems(it)
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
                        with(ProductFragment.productView) {
                            item_name.text = it.Name
                            item_code.text = it.Code
                            item_size.text = it.Description
                            item_price.text = it.Price.toString()
                            item_available.text = if (it.IsActive) "Yes" else "No"
                            item_material.text = it.Material
                            prod_img.setItems(it.ImageUrls)
                        }
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
