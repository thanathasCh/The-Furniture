package com.example.furnitureapp.views.cart


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.*
import com.example.furnitureapp.data.local.CartSharedPreference
import com.example.furnitureapp.data.local.PurchaseSharePreference
import com.example.furnitureapp.views.user.LoginFragment
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.data.repository.CartRepository
import com.example.furnitureapp.interfaces.ClickEventHandler
import com.example.furnitureapp.models.CartViewModel
import com.example.furnitureapp.models.ProductViewModel
import com.example.furnitureapp.services.AlertBuilder
import com.example.furnitureapp.views.main.MainActivity
import com.example.furnitureapp.views.product.ProductFragment
import com.example.furnitureapp.views.purchase.ConfirmPurchaseFragment
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.yes_no_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment(),ClickEventHandler {
    companion object Cart {
        lateinit var cartAdapter: CartAdapter
        val carts = arrayListOf<CartViewModel>()
    }

//    var currentKey: String = ""
//    var cartProduct = ArrayList<ProductViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        getAllSharePref()
        MainActivity.pageId = R.layout.fragment_cart
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
//        val arrayKey = currentKey.split(",")
        val listOfProduct = view.findViewById(R.id.recycler_view_cart) as RecyclerView
        val placeOrder = view.findViewById<View>(R.id.place_order)
        val delete = view.findViewById<View>(R.id.delete_cart) as ImageView
        MainActivity.mainSrl.isRefreshing = true


        val cartShare = CartSharedPreference(MainActivity.mainThis)
        val cartInLocal = cartShare.retrieveCarts()
        for (i in cartShare.retrieveCarts()) {
            e("Product in sharepref: ", i.toString())
        }
//        Log.e("Product After Add", cartShare.retrieveCarts().size.toString())

        delete.setOnClickListener {
            val alertBuilder = AlertBuilder()
            val alert = alertBuilder.showYesNoAlert("Delete", getString(R.string.delete_confirm))
            val adapter = recycler_view_cart.adapter as CartAdapter
            alert?.yes_btn?.setOnClickListener {
                if (!UserSharedPreference(MainActivity.mainThis).isLogin()) {
                    for (i in adapter.selectProudctPosition) {
                        adapter.removeAt(i)
                        cartShare.saveCarts(cartInLocal)
                    }
                }else{
                    for (i in adapter.selectProudctPosition){
                        CartRepository(MainActivity.mainThis).removeCart(i.Id){
                            e("delete? ", i.Product.toString())
                            e("delete? ", it.toString())
                            adapter.removeAt(i)
                        }
                    }
                }
                alertBuilder.dismiss()
            }
        }
        if (UserSharedPreference(MainActivity.mainThis).isLogin()) {
            CartRepository(MainActivity.mainThis).fetchCartByUserId(true) {
                e("size of db cart,",it.size.toString())
                var cart = it
                if (cart.size ==0){
                    display_empty.text = "Cart is empty"
                }
                cartAdapter = CartAdapter(cart, this)
                listOfProduct.layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
                listOfProduct.adapter = cartAdapter
                cartAdapter.notifyDataSetChanged()
                MainActivity.mainSrl.isRefreshing = false
            }
        } else {
            cartAdapter = CartAdapter(cartInLocal, this)
//            if (cartInLocal.size ==0){
//                display_empty.text = "Cart is empty"
//            }
            listOfProduct.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
            listOfProduct.adapter = cartAdapter
            CartRepository(MainActivity.mainThis).fetchCartByUserId(false) {
                carts.clear()
                carts.addAll(it)
                cartAdapter.notifyDataSetChanged()
                MainActivity.mainSrl.isRefreshing = false
            }
        }


        placeOrder.setOnClickListener {
            val alertBuilder = AlertBuilder()
            if (!UserSharedPreference(MainActivity.mainThis).isLogin()) {
                alertBuilder.showOkAlert(getString(R.string.login_required)) {
                    val login = LoginFragment()
                    val fragmentManager = activity!!.supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frame_layout, login)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            } else {
                val bundle = Bundle()
                bundle.putBoolean("cart", true)
                val purchaseSharePreference = PurchaseSharePreference(MainActivity.mainThis)
                purchaseSharePreference.savePurchase(cartAdapter.selectProudctPosition)
                val confirmPurchase = ConfirmPurchaseFragment()
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                confirmPurchase.arguments = bundle
                fragmentTransaction.replace(R.id.frame_layout, confirmPurchase)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

        }

        return view
    }

    fun getSharePref(name: String): String {
        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.apply()
        return sharedPref?.getString(name, null).toString()
    }

    override fun forwardClick(holder: View, product: ProductViewModel) {
        val bundle = Bundle()
        bundle.putString("id", product.Id)
        bundle.putString("name", product.Name)
        bundle.putString("size", product.Description)
        bundle.putString("code", product.Code)
        bundle.putDouble("price", product.Price)
        bundle.putStringArrayList("image", product.ImageUrls)
        bundle.putString("material", product.Material)
        bundle.putBoolean("available", product.IsActive)
        bundle.putInt("stock", product.ProductStock)
        val productFragment = ProductFragment()
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        productFragment.arguments = bundle
        fragmentTransaction.replace(R.id.frame_layout, productFragment)
        MainActivity.pageId = R.layout.fragment_product
        MainActivity.productId = product.Id ?: ""
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

//    private fun getAllSharePref() {
//        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
//        var key = sharedPref?.all
//
//        if (key != null) {
//            for (entry in key) {
//                currentKey += "$entry,"
//                e("global is", currentKey)
//            }
//        }
//    }
//    private fun returnSharePref():String{
//        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
//        var key = sharedPref?.all
//        var store = ""
//        if (key != null) {
//            for (entry in key) {
//                store += "$entry,"
//            }
//        }
//        return store
//    }
//    private fun removeKeySharePref(key:String){
//        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
//        val editor = sharedPref?.edit()
//        editor?.remove(key)
//        editor?.commit()
//    }
}
