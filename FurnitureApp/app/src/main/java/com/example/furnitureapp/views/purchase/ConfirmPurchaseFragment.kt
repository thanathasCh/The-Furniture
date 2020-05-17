package com.example.furnitureapp.views.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.*
import com.example.furnitureapp.data.local.PurchaseSharePreference
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.data.repository.AddressRepository
import com.example.furnitureapp.data.repository.CartRepository
import com.example.furnitureapp.models.*
import com.example.furnitureapp.views.address.AddressFragment
import com.example.furnitureapp.services.AlertBuilder
import com.example.furnitureapp.views.main.HomeFragment
import com.example.furnitureapp.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_confirm_purchase.view.*
import kotlinx.android.synthetic.main.fragment_confirm_purchase.view.con_phone_number
import kotlinx.android.synthetic.main.ok_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class ConfirmPurchaseFragment : Fragment() {

    var currentPurchaseItem = ArrayList<CartViewModel>()
    var isPickUp = true
    var addresses = ArrayList<AddressViewModel>()
    var index = ArrayList<Int>()
    val purchaseItems = ArrayList<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_confirm_purchase, container, false)
        val back = view.findViewById<View>(R.id.con_back) as ImageView
        val pickUp = view.findViewById<View>(R.id.pick_up) as Button
        val delivery = view.findViewById<View>(R.id.delivery) as Button
        val address = view.findViewById<View>(R.id.address) as RelativeLayout
        val placeOrder = view.findViewById<View>(R.id.btn_confirm_place_order)
        val totalPriceTV = view.findViewById<View>(R.id.total_price) as TextView
        currentPurchaseItem.clear()

        val id = arguments?.getString("id")
        val amount = arguments?.getInt("amount")
        val name = arguments?.getString("name")
        val code = arguments?.getString("code")
        val size = arguments?.getString("size")
        val price = arguments?.getDouble("price")
        val images = arguments?.getStringArrayList("image")
        val fromCart = arguments?.getBoolean("cart")
        val userSharePreference = UserSharedPreference(MainActivity.mainThis)
        var checkAddress = false

        if (isPickUp) {
            delivery.setBackgroundResource(R.drawable.grey_border)
            pickUp.setBackgroundResource(R.drawable.border)
        }

        AddressRepository(MainActivity.mainThis).fetchAddresses(false) {
            if (it.isNotEmpty()) {
                addresses.clear()
                addresses.addAll(it)
                checkAddress = true
                view.customer.text = addresses[0].Name
                view.con_phone_number.text = addresses[0].TelephoneNumber
                view.con_address_detail.text =
                    addresses[0].Road + ", " + addresses[0].Address + ", " + addresses[0].SubDistrict + ", " + addresses[0].District + ", " + addresses[0].Province + "."
                MainActivity.mainSrl.isRefreshing = false
            }
        }

        //RecyclerView
        if (fromCart == true) {
            var totalPurchasePrice = 0.0
            val purchaseSharePreference = PurchaseSharePreference(MainActivity.mainThis)
            val listOfConfirmPurchase = view.findViewById(R.id.purchase_recycler_view) as RecyclerView
            listOfConfirmPurchase.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)

            purchaseItems.clear()
            purchaseItems.addAll(purchaseSharePreference.retrievePurchase())

            listOfConfirmPurchase.adapter = ConfirmPurchaseAdapter(purchaseItems, this)

            for (i in purchaseItems) {
                totalPurchasePrice += (i.Quantity * i.Product.Price)
                totalPriceTV.text = totalPurchasePrice.toString()
            }
        } else {
            currentPurchaseItem.add(
                CartViewModel(
                    Id = userSharePreference.getUserId(), Product = ProductViewModel(
                        Id = id!!,
                        Name = name!!,
                        Price = price!!,
                        ImageUrls = images!!,
                        Code = code!!
                    ), Quantity = amount!!
                )
            )

            totalPriceTV.text = (price * amount).toString()
            val listOfConfirmPurchase = view.findViewById(R.id.purchase_recycler_view) as RecyclerView
            listOfConfirmPurchase.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
            listOfConfirmPurchase.adapter = ConfirmPurchaseAdapter(currentPurchaseItem, this)
        }
        pickUp.setOnClickListener {
            isPickUp = true
            delivery.setBackgroundResource(R.drawable.grey_border)
            pickUp.setBackgroundResource(R.drawable.border)
        }
        delivery.setOnClickListener {
            isPickUp = false
            pickUp.setBackgroundResource(R.drawable.grey_border)
            delivery.setBackgroundResource(R.drawable.border)
            address.setBackgroundResource(R.drawable.border)
        }


        //Button Action
        //set button
        placeOrder.setOnClickListener {
            MainActivity.mainSrl.isRefreshing = true
            val cartRepository = CartRepository(MainActivity.mainThis)
            val alertBuilder = AlertBuilder()
            if (checkAddress) {
                if (fromCart!!) {
                    cartRepository.purchaseCarts(
                        PurchaseSharePreference(MainActivity.mainThis).retrievePurchase(),
                        addresses[0].Id,
                        isPickUp
                    ) {
                        when {
                            it == null -> {
                                alertBuilder.showOkAlert("Error Occurred", "Error Occurred")
                            }
                            it.isEmpty() -> {
                                alertBuilder.showOkAlertWithAction(
                                    "Purchase",
                                    getString(R.string.success)
                                ).ok_btn.setOnClickListener {
                                    val home = HomeFragment()
                                    val fragmentManager = activity!!.supportFragmentManager
                                    val fragmentTransaction = fragmentManager.beginTransaction()
                                    fragmentTransaction.replace(R.id.frame_layout, home)
                                    fragmentTransaction.addToBackStack(null)
                                    fragmentTransaction.commit()
                                    alertBuilder.dismiss()
                                }
                            }
                            else -> {
                                val notAvailableProduct =
                                    purchaseItems.filter { x -> it.contains(x.ProductId) }
                                        .joinToString { x -> x.Product.Name }
                                alertBuilder.showOkAlert(getString(R.string.purchase_fail), getString(R.string.product_quantity_not_available, notAvailableProduct))
                            }
                        }
                        MainActivity.mainSrl.isRefreshing = false
                    }
                } else {
                    cartRepository.purchaseProduct(id!!, amount!!, addresses[0].Id, isPickUp) {
                        if (it) {

                            alertBuilder.showOkAlertWithAction(
                                "Purchase",
                                getString(R.string.success)
                            ).ok_btn.setOnClickListener {
                                val home = HomeFragment()
                                val fragmentManager = activity!!.supportFragmentManager
                                val fragmentTransaction = fragmentManager.beginTransaction()
                                fragmentTransaction.replace(R.id.frame_layout, home)
                                fragmentTransaction.addToBackStack(null)
                                fragmentTransaction.commit()
                                alertBuilder.dismiss()
                            }
                        } else {
                            alertBuilder.showOkAlert("Purchase", "Purchase Failed")
                        }
                    }
                }
            } else {
                AlertBuilder().showOkAlert("Address Missing", "Please fill your address")
                MainActivity.mainSrl.isRefreshing = false
            }

        }


        address.setOnClickListener {
            val address = AddressFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, address)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        back.setOnClickListener {
            val fragment = activity!!.supportFragmentManager
            fragment.popBackStack()
        }
        return view
    }


    // implement Function
//    fun findProduct(id: String, amount: Int) {
////        currentPurchaseItem.clear()
//        for (i in productData) {
//            if (i.id.equals(id)) {
//                currentPurchaseItem.add(
//                    Product(
//                        i.id.toString(),
//                        i.name.toString(),
//                        i.size.toString(),
//                        i.code.toString(),
//                        i.price,
//                        i.image,
//                        i.material.toString(),
//                        amount
//                    )
//                )
//            }
//        }
//        for (i in 0 until productData.size-1){
//            if (productData[i].id.equals(id)){
//                index.add(i)
//            }
//        }
//    }


}
