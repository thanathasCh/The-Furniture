package com.example.furnitureapp.views.product


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.custom.sliderimage.logic.SliderImage
import com.example.furnitureapp.views.main.MainActivity
import com.example.furnitureapp.views.purchase.PurchaseFragment
import com.example.furnitureapp.R
import com.example.furnitureapp.data.local.CartSharedPreference
import com.example.furnitureapp.views.user.LoginFragment
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.models.CartViewModel
import com.example.furnitureapp.services.AlertBuilder
import com.example.furnitureapp.services.ToastBuilder
import kotlinx.android.synthetic.main.fragment_product.view.*


/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment() {

    companion object Product {
        lateinit var productView: View
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        productView = inflater.inflate(R.layout.fragment_product, container, false)

        val back = productView.findViewById<View>(R.id.back_product_btn)
        val img = productView.findViewById<View>(R.id.prod_img) as SliderImage
        val addToCart = productView.findViewById<View>(R.id.add_to_cart)
        val purchase = productView.findViewById<View>(R.id.purchase)
//        clearSharePref()
        val id = arguments?.getString("id")
        val name = arguments?.getString("name")
        val code = arguments?.getString("code")
        val size = arguments?.getString("size")
        val price = arguments?.getDouble("price")
        val images = arguments?.getStringArrayList("image")
        val material = arguments?.getString("material")
        val available = arguments?.getBoolean("available")

        productView.item_name.text = name
        productView.item_code.text = code
        productView.item_size.text = size
        productView.item_price.text = price.toString()
        productView.item_available.text = if (available == true) "Yes" else "No"
        productView.item_material.text = material.toString()
        if (images != null) {
            img.setItems(images)
        }


        back.setOnClickListener {
            val fragmentManager = activity!!.supportFragmentManager
            MainActivity.pageId = R.layout.fragment_browse_item
            fragmentManager.popBackStack()
        }
        addToCart.setOnClickListener {
            if (id != null) {
                val cartShare = CartSharedPreference(MainActivity.mainThis)
                val carts = cartShare.retrieveCarts()
                Log.e("Product Before Add", carts.size.toString())
                carts.add(CartViewModel(ProductId=id))
                cartShare.saveCarts(carts)
                storeSharePref(code.toString(), id)
                ToastBuilder().createShortToast(getString(R.string.added_cart))
            }
        }


        purchase.setOnClickListener {
            val alertBuilder = AlertBuilder()

            if (!available!!) {
                alertBuilder.showOkAlert(getString(R.string.product_not_available))
            } else {
                MainActivity.mainSrl.isRefreshing = true
                if (!UserSharedPreference(MainActivity.mainThis).isLogin()) {
                    alertBuilder.showYesNoAlert(getString(R.string.login_required),
                        yesOpt = {
                            val login = LoginFragment()
                            val fragmentManager = activity!!.supportFragmentManager
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.frame_layout, login)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        })
                } else {
                    val bundle = Bundle()
                    val purchaseFragment = PurchaseFragment()
                    bundle.putString("id", id)
                    val fragmentManager = activity!!.supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    purchaseFragment.arguments = bundle
                    fragmentTransaction.replace(R.id.frame_layout, purchaseFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }

                MainActivity.mainSrl.isRefreshing = false
            }
        }

        return productView
    }

    private fun storeSharePref(code:String, id: String) {
        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putString(code, id)
        editor?.apply()
    }

    fun clearSharePref() {
        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()?.clear()
        editor?.apply()
    }


}
