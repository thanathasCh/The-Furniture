package com.example.furnitureapp.Address

import android.graphics.Canvas
import android.graphics.Color.parseColor
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.*
import com.example.furnitureapp.BrowseItem.BrowseItemFragment
import com.example.furnitureapp.models.Address
import com.example.furnitureapp.models.AddressController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.address_cell.view.*
import kotlinx.android.synthetic.main.fragment_address.*
import kotlinx.android.synthetic.main.fragment_confirm_purchase.*
import kotlinx.android.synthetic.main.fragment_confirm_purchase.view.*
import kotlinx.android.synthetic.main.fragment_confirm_purchase.view.con_phone_number
import kotlinx.android.synthetic.main.fragment_create_new.view.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class AddressFragment : Fragment(),Communicator {

    var addressList =AddressController()
    var adapter: AddressAdapter? = null
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private var colorDrawableBackground = ColorDrawable(parseColor("#f7f7f7"))
    private lateinit var deleteIcon: Drawable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_address, container, false)
        var back = view.findViewById<View>(R.id.address_back) as ImageView
        var add = view.findViewById<View>(R.id.add_address) as ImageView


        back.setOnClickListener {
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }
        add.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("add",true)
            val editAddress = EditAddressFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            editAddress.arguments = bundle
            fragmentTransaction.replace(R.id.frame_layout,editAddress)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }






//  Recycler View
        val listOfAddress =  view.findViewById<RecyclerView>(R.id.recycler_view_address) as RecyclerView
        listOfAddress.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,true)
        listOfAddress.adapter = AddressAdapter(allUser[userIndex!!].addressList,this)

        val swipeHandler = object : SwipeToDeleteCallback(this.activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recycler_view_address.adapter as AddressAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler_view_address)


        return view
    }


    override fun clickListener(holder: View) {
    }

    override fun clickWithDataTransfer(holder: View, id: String) {
        val bundle = Bundle()
        bundle.putString("id",id)
        val editAddress = EditAddressFragment()
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        editAddress.arguments = bundle
        fragmentTransaction.replace(R.id.frame_layout,editAddress)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


}
