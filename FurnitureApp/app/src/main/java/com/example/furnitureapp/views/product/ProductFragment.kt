package com.example.furnitureapp.views.product


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Log.e
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
import com.example.furnitureapp.data.repository.CartRepository
import com.example.furnitureapp.models.CartViewModel
import com.example.furnitureapp.models.ProductViewModel
import com.example.furnitureapp.services.AlertBuilder
import com.example.furnitureapp.services.ToastBuilder
import kotlinx.android.synthetic.main.fragment_product.view.*
import kotlinx.android.synthetic.main.yes_no_dialog.*
import kotlinx.android.synthetic.main.yes_no_dialog.view.*
import kotlinx.android.synthetic.main.yes_no_dialog.view.no_btn
import kotlinx.android.synthetic.main.yes_no_dialog.view.yes_btn


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
        val stock = arguments?.getInt("stock")

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
            if (UserSharedPreference(MainActivity.mainThis).isLogin()) {
                var cartRepository = CartRepository(MainActivity.mainThis)
                id?.let { it1 ->
                    cartRepository.addCart(it1) {
                        Log.e("Success ?", it.toString())
                    }
                }
                ToastBuilder().createShortToast(getString(R.string.added_cart))
            } else {
                if (id != null) {
                    val cartShare = CartSharedPreference(MainActivity.mainThis)
                    val carts = cartShare.retrieveCarts()
                    var exist = false
                    for (i in carts){
                        if (id == i.ProductId){
                            i.Quantity += 1
                            exist = true
                        }
                    }
                    if (!exist){
                        carts.add(
                            CartViewModel(
                                ProductId = id,
                                Quantity = 1,
                                Id = id,
                                Product = ProductViewModel(
                                    Name = name!!,
                                    ImageUrls = images!!,
                                    Code = code!!,
                                    Price = price!!,
                                    Description = size!!,
                                    Material = material!!,
                                    IsActive = available!!,
                                    ProductStock = stock!!
                                )
                            )
                        )
                    }
                    e("check exit,", exist.toString())
                    cartShare.saveCarts(carts)
                    ToastBuilder().createShortToast(getString(R.string.added_cart))
                }
            }
        }


        purchase.setOnClickListener {
            val alertBuilder = AlertBuilder()


            if (!available!!) {

                alertBuilder.showOkAlert("Purchase Failed",getString(R.string.product_not_available))
                alertBuilder.dismiss()
            } else {
                MainActivity.mainSrl.isRefreshing = true
                if (!UserSharedPreference(MainActivity.mainThis).isLogin()) {
                    val alert =
                        alertBuilder.showYesNoAlert("Login", getString(R.string.login_required))
                    alert?.yes_btn?.setOnClickListener {
                        val login = LoginFragment()
                        val fragmentManager = activity!!.supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.frame_layout, login)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                        alertBuilder.dismiss()
                    }
                } else {
                    val bundle = Bundle()
                    val purchaseFragment = PurchaseFragment()
                    bundle.putString("id", id)
                    bundle.putString("name", name)
                    bundle.putString("code", code)
                    bundle.putStringArrayList("image", images)
                    if (price != null) {
                        bundle.putDouble("price", price)
                    }
                    if (stock != null) {
                        bundle.putInt("stock", stock)
                    }
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

    private fun storeSharePref(code: String, id: String) {
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
