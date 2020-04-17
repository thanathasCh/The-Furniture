package com.example.furnitureapp.Cart


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import com.example.furnitureapp.Purchase.ConfirmPurchaseAdapter
import com.example.furnitureapp.Purchase.ConfirmPurchaseFragment
import com.example.furnitureapp.User.LoginFragment
import com.example.furnitureapp.data.api.ProductApi
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.models.CategoriesController
import com.example.furnitureapp.models.ProductController
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.models.ProductViewModel
import kotlinx.android.synthetic.main.fragment_cart.*

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {


    var currentKey: String = ""
    var cartProduct = ArrayList<ProductViewModel>()
    var categories = CategoriesController()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getAllSharePref()
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        val arrayKey = currentKey.split(",")
        val listOfProduct = view.findViewById<RecyclerView>(R.id.recycler_view_cart) as RecyclerView
        val placeOrder = view.findViewById<View>(R.id.place_order)
        val delete = view.findViewById<View>(R.id.delete_cart) as ImageView
        var adapter = CartAdapter(cartProduct, this)
        for (i in 0 until arrayKey.size - 1){
            ProductApi().getProductById(arrayKey[i].substring(5,arrayKey[i].length)){
                if (!cartProduct.contains(it)){
                    e("it code: ", it.Code)
                    cartProduct.add(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        delete.setOnClickListener {
            if (!UserSharedPreference(MainActivity.mainThis).isLogged()) {
                val adapter = recycler_view_cart.adapter as CartAdapter
                for (i in adapter.selectProudctPosition) {
                    adapter.removeAt(i)
                    e("key is: ",i.Code +"="+ i.Id.toString() )
                    removeKeySharePref(i.Code.toString())
                    e("after delete:",returnSharePRef())
                }
            }
        }




        listOfProduct.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
        listOfProduct.adapter =
            adapter


        placeOrder.setOnClickListener {
            if (!UserSharedPreference(MainActivity.mainThis).isLogged()) {
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle("Please Login Before Make Purchase")
                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
                    val login = LoginFragment()
                    val fragmentManager = activity!!.supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frame_layout, login)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()

                }
                builder.show()
            } else {
                val bundle = Bundle()
                bundle.putStringArrayList("cart product", adapter.selectProductCode)
                bundle.putIntegerArrayList("cart amount", adapter.selectProductAmount)
                val confirmPurchse = ConfirmPurchaseFragment()
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                confirmPurchse.arguments = bundle
                fragmentTransaction.replace(R.id.frame_layout, confirmPurchse)
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

    fun getAllSharePref() {
        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
        var key = sharedPref?.all

        if (key != null) {
            for (entry in key) {
                currentKey += "$entry,"
                e("global is", currentKey)
            }
        }
    }
    fun returnSharePRef():String{
        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
        var key = sharedPref?.all
        var store = ""
        if (key != null) {
            for (entry in key) {
                store += "$entry,"
            }
        }
        return store
    }
    fun removeKeySharePref(key:String){
        val sharedPref = this.activity?.getSharedPreferences("Furniture", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.remove(key)
        editor?.commit()
    }



}
