package com.example.furnitureapp.Purchase

import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.Address.AddressFragment
import com.example.furnitureapp.Product.ProductFragment
import com.example.furnitureapp.R
import com.example.furnitureapp.models.Address
import com.example.furnitureapp.models.AddressController
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.models.ProductController
import kotlinx.android.synthetic.main.address_cell.view.*
import kotlinx.android.synthetic.main.fragment_confirm_purchase.view.*
import kotlinx.android.synthetic.main.fragment_confirm_purchase.view.con_phone_number

/**
 * A simple [Fragment] subclass.
 */
class ConfirmPurchaseFragment : Fragment() {

    var currentPurchaseItem = ArrayList<Product>()
    var product = ProductController()
    var isPickUp = true
    var addressList = AddressController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_confirm_purchase, container, false)
        var back = view.findViewById<View>(R.id.con_back) as ImageView
        var pick_up = view.findViewById<View>(R.id.pick_up) as Button
        var delivery = view.findViewById<View>(R.id.delivery) as Button
        var address = view.findViewById<View>(R.id.address) as RelativeLayout
        currentPurchaseItem.clear()
//        createAddress()

        var id = arguments?.getString("id")
        var amount = arguments?.getInt("amount")

        for(address in addressList.getAddress()){
            e("address",address.isCurrentAddress.toString())
            if(address.isCurrentAddress!!){
                view.customer.text = address.name
                view.con_phone_number.text = address.phoneNumber
                view.con_address_detail.text = address.road+", "+address.house+", "+address.sub_district+", "+address.district+", "+address.province+"."
            }
        }

        findProduct(id.toString(),amount!!)
        val listOfConfirmPurchase = view.findViewById<RecyclerView>(R.id.purchase_recycler_view) as RecyclerView
        listOfConfirmPurchase.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,true)
        listOfConfirmPurchase.adapter = ConfirmPurchaseAdapter(currentPurchaseItem,this)
        delivery.setBackgroundResource(R.drawable.grey_border)
        pick_up.setOnClickListener {
            isPickUp = true
            delivery.setBackgroundResource(R.drawable.grey_border)
            pick_up.setBackgroundResource(R.drawable.border)
            address.setBackgroundResource(R.drawable.grey_border)
        }
        delivery.setOnClickListener {
            isPickUp = false
            pick_up.setBackgroundResource(R.drawable.grey_border)
            delivery.setBackgroundResource(R.drawable.border)
            address.setBackgroundResource(R.drawable.border)
        }
        address.setOnClickListener {
            val address = AddressFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,address)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }





        back.setOnClickListener {
            val fragment = activity!!.supportFragmentManager
            fragment.popBackStack()
        }
        return view
    }
    fun findProduct(id:String,amount:Int){
        for (i in product.createMockUp()){
            if (i.id.equals(id)){
                currentPurchaseItem.clear()
                currentPurchaseItem.add(Product(i.id.toString(),i.name.toString(),i.size.toString(),i.code.toString(),i.price,i.image,i.material.toString(),amount))
                i.available -= amount
            }
        }
    }
//    fun createAddress(){
//        addressList.setAddress(Address("Xell","Road 123", "House 456","Bang Bo","Bang Bo", "Samut Prakarn",true,"012-345-6789"))
//        addressList.setAddress(Address("Menh","Road 2004", "House 576","Thong Lor","Thong Lor", "Bangkok",false,"098-765-4321"))
//    }



}
