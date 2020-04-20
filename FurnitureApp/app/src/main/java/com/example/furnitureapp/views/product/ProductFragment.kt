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
import com.example.furnitureapp.MainActivity
import com.example.furnitureapp.views.purchase.PurchaseFragment
import com.example.furnitureapp.R
import com.example.furnitureapp.views.user.LoginFragment
import com.example.furnitureapp.services.createShortToast
import com.example.furnitureapp.data.local.UserSharedPreference
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
        Log.d("DEBUG", "here")
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
                storeSharePref(code.toString(), id)
                createShortToast(
                    MainActivity.mainThis,
                    getString(R.string.added_cart)
                )
            }
        }


        purchase.setOnClickListener {
            if (!available!!) {
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle(getString(R.string.product_not_available))
                builder.setPositiveButton("Okay") { _: DialogInterface?, _: Int ->
                }
                builder.show()
            } else {
                MainActivity.mainSrl.isRefreshing = true
                if (!UserSharedPreference(MainActivity.mainThis).isLogin()) {
                    val builder = AlertDialog.Builder(this.activity)
                    builder.setTitle(getString(R.string.login_required))
                    builder.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                        val login =
                            LoginFragment()
                        val fragmentManager = activity!!.supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.frame_layout, login)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }
                    builder.setNegativeButton("No") { _: DialogInterface?, _: Int ->
                    }
                    builder.show()

                } else {
                    val bundle = Bundle()
                    val purchaseFragment =
                        PurchaseFragment()
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

    fun storeSharePref(code:String,id: String) {
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
