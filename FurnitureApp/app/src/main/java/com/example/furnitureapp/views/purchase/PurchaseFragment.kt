package com.example.furnitureapp.views.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.furnitureapp.R
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.models.ProductController
import com.example.furnitureapp.services.productData
import com.example.furnitureapp.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_purchase.view.*

/**
 * A simple [Fragment] subclass.
 */
class PurchaseFragment : Fragment() {
    val product = ProductController()
    var currentProduct: Product? = null
    var id = ""
    var currentAmount = 1
    var productIndex: Int? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_purchase, container, false)
        id = arguments?.getString("id").toString()
        val name = arguments?.getString("name")
        val code = arguments?.getString("code")
        val size = arguments?.getString("size")
        val price = arguments?.getDouble("price")
        val images = arguments?.getStringArrayList("image")
        val material = arguments?.getString("material")
        val available = arguments?.getBoolean("available")
        val stock = arguments?.getInt("stock")
        var totalPrice = price

//      UI Element
        var img = view.findViewById<View>(R.id.pur_img) as ImageView
        var increase = view.findViewById<View>(R.id.pur_increase)
        var amount = view.findViewById<View>(R.id.buy_amount) as EditText
        var decrease = view.findViewById<View>(R.id.pur_reduce)
        var next = view.findViewById<View>(R.id.purchase) as Button
        var cancel = view.findViewById<View>(R.id.cancel) as Button

//      set property
        Glide.with(MainActivity.mainThis).load(images?.get(0)).placeholder(R.drawable.loading)
            .into(img)
        view.pur_name.text = name
        view.pur_code.text = code
        view.pur_price.text = price.toString()



        increase.setOnClickListener {
            if (currentAmount.equals(stock)) {
                increase.isEnabled = false
            } else {
                if (totalPrice != null) {
                    if (price != null) {
                        totalPrice += price
                    }
                }
                view.pur_price.text = totalPrice.toString()
                currentAmount += 1
                decrease.isEnabled = true
                amount.setText(currentAmount.toString())
            }

        }

        decrease.setOnClickListener {
            if (currentAmount.equals(1)) {
                decrease.isEnabled = false
            } else {
                if (totalPrice != null) {
                    if (price != null) {
                        totalPrice -= price
                    }
                }
                view.pur_price.text = totalPrice.toString()
                increase.isEnabled = true
                currentAmount -= 1
                amount.setText(currentAmount.toString())
            }

        }

        next.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", id)
            bundle.putString("name",name)
            bundle.putString("code",code)
            bundle.putStringArrayList("image",images)
            bundle.putInt("amount", currentAmount)
            if (price != null) {
                bundle.putDouble("price", price)
            }
            val confirmPurchase = ConfirmPurchaseFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            confirmPurchase.arguments = bundle
            fragmentTransaction.replace(R.id.frame_layout, confirmPurchase)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        cancel.setOnClickListener {
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }

        return view
    }



}
