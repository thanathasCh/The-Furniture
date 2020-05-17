package com.example.furnitureapp.views.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentTransaction
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.furnitureapp.R
import com.example.furnitureapp.data.api.*
import com.example.furnitureapp.views.browseItem.BrowseItemFragment
import com.example.furnitureapp.views.cart.CartFragment
import com.example.furnitureapp.views.category.CategoriesFragment
import com.example.furnitureapp.views.product.ProductFragment
import com.example.furnitureapp.views.user.UnRegisterFragment
import com.example.furnitureapp.views.user.UserFragment
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.data.repository.AddressRepository
import com.example.furnitureapp.data.repository.CartRepository
import com.example.furnitureapp.data.repository.CategoryRepository
import com.example.furnitureapp.interfaces.PageInterface
import com.example.furnitureapp.models.*
import com.example.furnitureapp.services.Page
import com.example.furnitureapp.views.address.AddressFragment
import com.example.furnitureapp.views.user.UserPurchaseListFragment
import com.example.furnitureapp.views.user.UserSettingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product.view.*
import kotlinx.android.synthetic.main.fragment_user_setting.view.*

class MainActivity : AppCompatActivity(), PageInterface {

    lateinit var homeFragment: HomeFragment
    lateinit var cartFragment: CartFragment
    lateinit var userFragment: UserFragment


    companion object PageObjects {
        lateinit var mainThis: Context
        lateinit var mainSrl: SwipeRefreshLayout
        lateinit var pageId: Page
        var categories = arrayListOf<CategoryViewModel>()
        var products = arrayListOf<ProductViewModel>()
        var categoryId = ""
        var productId = ""
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainThis = this
        val bottomNavigation: BottomNavigationView = findViewById(R.id.btm_navig)
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    homeFragment =
                        HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.cart -> {
                    cartFragment = CartFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, cartFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.user -> {
                    if (UserSharedPreference(mainThis).isLogin()) {
                        userFragment = UserFragment()
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, userFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                    } else {
                        val unRegisterUser = UnRegisterFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, unRegisterUser)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()
                    }

                }
            }
            true
        }
        fun AppCompatActivity.hideKeyboard() {
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            // else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            // }
        }

        main_srl.setOnRefreshListener {
            main_srl.setColorSchemeColors(android.R.color.holo_blue_bright,android.R.color.holo_green_light,android.R.color.holo_orange_light)
            Log.d("DEBUG", pageId.toString())
            when (pageId) {
                Page.HOME -> {
                    AnnouncementApi().getAnnouncementImages {
                        HomeFragment.slider.setItems(it)
                    }
                    main_srl.isRefreshing = false
                }
                Page.CART -> {
                    CartRepository(this).fetchCartByUserId(true) {
                        with (CartFragment.carts) {
                            clear()
                            addAll(it)
                        }
                        CartFragment.cartAdapter.notifyDataSetChanged()
                        main_srl.isRefreshing = false
                    }
                }
                Page.SEARCH -> {
                    CategoryRepository(this).fetchCategory(true) {
                        categories.clear()
                        categories.addAll(it)
                        CategoriesFragment.categoryAdapter.notifyDataSetChanged()
                        main_srl.isRefreshing = false
                    }
                }
                Page.PRODUCTS -> {
                    ProductApi().getProductByCategoryId(categoryId) {
                        products.clear()
                        products.addAll(it)
                        BrowseItemFragment.browseAdapter.notifyDataSetChanged()
                        main_srl.isRefreshing = false
                    }
                }
                Page.PRODUCT -> {
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
                Page.USER -> {
                    val user = UserSharedPreference(this).retrieveUser()
                    UserApi().getUser(user.Id ?: "") {
                        with(UserSettingFragment.userSettingView) {
                            welcome_txt.text = getString(R.string.greeting_user, it.FirstName, it.LastName)
                            setting_name.text = it.getFullName()
                            setting_phone.text = it.TelephoneNumber
                            setting_email.text = it.Email
                        }
                    }
                    main_srl.isRefreshing = false
                }
                Page.ADDRESS -> {
                    AddressRepository(this).fetchAddresses(true) {
                        with (AddressFragment) {
                            addresses.clear()
                            addresses.addAll(it)
                            addressAdapter.notifyDataSetChanged()
                        }
                    }
                    main_srl.isRefreshing = false
                }
                Page.PURCHASE -> {
                    TransactionApi().getTransaction {
                        with(UserPurchaseListFragment) {
                            purchases.clear()
                            purchases.addAll(it)
                            userPurchaseAdapter.notifyDataSetChanged()
                            main_srl.isRefreshing = false
                        }
                    }
                }
                else -> {
                    main_srl.isRefreshing = false
                }
            }
        }

        mainSrl = main_srl
    }
}
