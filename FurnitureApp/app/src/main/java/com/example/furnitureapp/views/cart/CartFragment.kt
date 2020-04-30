package com.example.furnitureapp.views.cart


import android.content.Context
import android.os.Bundle
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
import com.example.furnitureapp.views.user.LoginFragment
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.data.repository.CartRepository
import com.example.furnitureapp.models.CartViewModel
import com.example.furnitureapp.models.ProductViewModel
import com.example.furnitureapp.services.AlertBuilder
import com.example.furnitureapp.views.main.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {
    companion object Cart {
        lateinit var cartAdapter: CartAdapter
        val carts = arrayListOf<CartViewModel>()
        val product = arrayListOf<ProductViewModel>()
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

        val cart = activity?.let { CartSharedPreference(it) }
        val cartPref = CartSharedPreference(MainActivity.mainThis)
        e("product in shared", cartPref.retrieveCarts().toString())
//        e("product in share pref: ",cart.toString())
//        if (cart != null) {
////            cart.retrieveCarts().add((CartViewModel(ProductId = "hello", Quantity = 1,Product = ProductViewModel(Id="1",Name = "h",Price = 1.1))))
//            for (i in cart.retrieveCarts()){
//                e("product in share pref: ",i.toString())
//                e("size:  ",cart.retrieveCarts().size.toString())
//            }
//        }else{
//            e("cart is, ","null")
//        }

        val listOfProduct = view.findViewById(R.id.recycler_view_cart) as RecyclerView
        val placeOrder = view.findViewById<View>(R.id.place_order)
        val delete = view.findViewById<View>(R.id.delete_cart) as ImageView
        MainActivity.mainSrl.isRefreshing = true
        if (cart != null) {
            listOfProduct.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
            cartAdapter = CartAdapter(cart.retrieveCarts(), this)

            listOfProduct.adapter = cartAdapter
        }

//        delete.setOnClickListener {
//            if (!UserSharedPreference(MainActivity.mainThis).isLogin()) {
//                val adapter = recycler_view_cart.adapter as CartAdapter
//                for (i in adapter.selectProudctPosition) {
//                    adapter.removeAt(i)
//                    e("key is: ",i.Code +"="+ i.Id.toString() )
//                    removeKeySharePref(i.Code.toString())
//                    e("after delete:", returnSharePref())
//                }
//            }
//        }


        CartRepository(MainActivity.mainThis).fetchCartByUserId(false) {
            carts.clear()
            carts.addAll(it)
            cartAdapter.notifyDataSetChanged()
            MainActivity.mainSrl.isRefreshing = false
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
//                val bundle = Bundle()
//                bundle.putStringArrayList("cart product", adapter.selectProductCode)
//                bundle.putIntegerArrayList("cart amount", adapter.selectProductAmount)
//                val confirmPurchse = ConfirmPurchaseFragment()
//                val fragmentManager = activity!!.supportFragmentManager
//                val fragmentTransaction = fragmentManager.beginTransaction()
//                confirmPurchse.arguments = bundle
//                fragmentTransaction.replace(R.id.frame_layout, confirmPurchse)
//                fragmentTransaction.addToBackStack(null)
//                fragmentTransaction.commit()
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
