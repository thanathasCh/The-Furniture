package com.example.furnitureapp.views.address

import android.graphics.Color.parseColor
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.*
import com.example.furnitureapp.data.repository.AddressRepository
import com.example.furnitureapp.interfaces.Communicator
import com.example.furnitureapp.models.AddressViewModel
import com.example.furnitureapp.services.AlertBuilder
import com.example.furnitureapp.views.main.MainActivity
import com.example.furnitureapp.views.shared.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_address.*
import kotlinx.android.synthetic.main.yes_no_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class AddressFragment : Fragment(),
    Communicator {

    companion object Address {
        val addresses = ArrayList<AddressViewModel>()
        lateinit var addressAdapter: AddressAdapter
    }

    var currentUserAddress = ArrayList<Address>()
    var adapter: AddressAdapter? = null
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private var colorDrawableBackground = ColorDrawable(parseColor("#f7f7f7"))
    private lateinit var deleteIcon: Drawable
    lateinit var addressAdapter: AddressAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_address, container, false)
        var back = view.findViewById<View>(R.id.address_back) as ImageView
        var add = view.findViewById<View>(R.id.add_address) as ImageView
        MainActivity.pageId = R.layout.fragment_address
        val listOfAddress =  view.findViewById(R.id.recycler_view_address) as RecyclerView
        listOfAddress.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,true)

        MainActivity.mainSrl.isRefreshing = true
        AddressRepository(MainActivity.mainThis).fetchAddresses(false) {
            addresses.clear()
            addresses.addAll(it)
            addressAdapter = AddressAdapter(addresses, this)
            listOfAddress.adapter = addressAdapter
            addressAdapter.notifyDataSetChanged()
            MainActivity.mainSrl.isRefreshing = false
        }

        back.setOnClickListener {
            val fragmentManager = activity!!.supportFragmentManager
            MainActivity.pageId = R.id.user
            fragmentManager.popBackStack()
        }

        add.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("add", true)
            val editAddress = EditAddressFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            editAddress.arguments = bundle
            fragmentTransaction.replace(R.id.frame_layout,editAddress)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

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

    override fun clickToSelect(holder: View, index: Int) {
        var alertBuilder = AlertBuilder()
        alertBuilder.showYesNoAlert("Address","Set as Current Address").yes_btn.setOnClickListener {
            AddressRepository(MainActivity.mainThis).fetchAddresses(false) {
                var address = it
                var saveAddress = address[0]
                address[0] = address[index]
                address[index] = saveAddress

                addresses.clear()
                addresses.addAll(address)
                addressAdapter.notifyDataSetChanged()

                AddressRepository(MainActivity.mainThis).saveAddresses(address)
                MainActivity.mainSrl.isRefreshing = false
                alertBuilder.dismiss()
            }
        }
    }

    override fun clickListener(holder: View, id: String?) {
        TODO("Not yet implemented")
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
