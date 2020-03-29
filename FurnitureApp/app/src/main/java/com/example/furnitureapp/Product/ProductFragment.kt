package com.example.furnitureapp.Product


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.furnitureapp.BrowseItem.BrowseItemFragment
import com.example.furnitureapp.Purchase.PurchaseFragment
import com.example.furnitureapp.R
import com.example.furnitureapp.User.LoginFragment
import com.example.furnitureapp.isLogin
import com.example.furnitureapp.models.ProductController
import kotlinx.android.synthetic.main.fragment_product.view.*


/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment() {

// TODO set all the detail according to each id name


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        val back = view.findViewById<View>(R.id.back_product_btn)
        val img = view.findViewById<View>(R.id.prod_img) as ImageView
        val addToCart = view.findViewById<View>(R.id.add_to_cart)
        val purchase = view.findViewById<View>(R.id.purchase)
        var mApp = ProductController()
        var id = arguments?.getString("id")
        var name = arguments?.getString("name")
        var code = arguments?.getString("code")
        var size = arguments?.getString("size")
        var price = arguments?.getFloat("price")
        var image = arguments?.getInt("image")
        var material = arguments?.getString("material")
        var available = arguments?.getInt("available")
        e("id is", id.toString())
        view.item_name.text = name
        view.item_code.text = code
        view.item_size.text = size
        view.item_price.text = price.toString()
        view.item_available.text = available.toString()
        view.item_material.text = material.toString()
        image?.let { img.setImageResource(it) }


        back.setOnClickListener {
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }
        addToCart.setOnClickListener {
            if (id != null) {
                storeSharePref(id)
            }
        }

        purchase.setOnClickListener{
            if (!isLogin){
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle("Please Login Before Making Purchase")
                builder.setPositiveButton("Yes") { dialogInterface: DialogInterface?, i: Int ->
                    val login =
                        LoginFragment()
                    val fragmentManager = activity!!.supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frame_layout, login)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
                builder.setNegativeButton("No"){ dialogInterface: DialogInterface?, i: Int ->
                }
                builder.show()

            }else{
                val bundle = Bundle()
                val purchase =
                    PurchaseFragment()
                bundle.putString("id",id)
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                purchase.arguments = bundle
                fragmentTransaction.replace(R.id.frame_layout, purchase)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

        }


        return view
    }
    fun storeSharePref(name: String) {
        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putString(name, name)
        editor?.apply()
    }

//    fun clearSharePref() {
//        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
//        val editor = sharedPref?.edit()?.clear()
//        editor?.apply()
//    }


}
