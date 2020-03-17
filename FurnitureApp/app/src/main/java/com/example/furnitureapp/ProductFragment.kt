package com.example.furnitureapp


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.furnitureapp.BrowseItem.BrowseItemFragment
import com.example.furnitureapp.models.Product
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
        var mApp = Singleton()

        var name = arguments?.getString("name")
        var code = arguments?.getString("code")
        var size = arguments?.getString("size")
        var price = arguments?.getFloat("price")
        var image = arguments?.getInt("image")
        var material = arguments?.getString("material")
        var available = arguments?.getInt("available")

        view.item_name.text = name
        view.item_code.text = code
        view.item_size.text = size
        view.item_price.text = price.toString()
        view.item_available.text = available.toString()
        view.item_material.text = material.toString()
        image?.let { img.setImageResource(it) }


        back.setOnClickListener {
            val browse =
                BrowseItemFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, browse)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        addToCart.setOnClickListener {
            mApp.globalVar.add(
                Product(
                    name.toString(), size.toString(), code.toString(),
                    price!!, image!!, material.toString(), available!!
                )
            )
        }


        return view
    }
    fun getSharePref(name: String): String {
        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.apply()
        return sharedPref?.getString(name, null).toString()
    }


}
