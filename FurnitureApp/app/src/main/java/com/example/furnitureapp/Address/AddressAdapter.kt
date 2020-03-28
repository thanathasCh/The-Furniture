package com.example.furnitureapp.Address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.ClickEventHandler
import com.example.furnitureapp.R
import com.example.furnitureapp.models.Address
import kotlinx.android.synthetic.main.address_cell.view.*

class AddressAdapter(
    val address: ArrayList<Address>,
    val context: AddressFragment
) : RecyclerView.Adapter<AddressAdapter.CustomViewHolder>() {


//    private val clickHandler: ClickEventHandler = context
    var bundle = Bundle()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.address_cell, parent, false)

        return CustomViewHolder(
            cellForRow
        )
    }


    override fun getItemCount(): Int {
        return address.size
    }



    fun removeAt(position: Int) {
        address.removeAt(position)
        notifyItemRemoved(position)
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val address = address[position]
        holder.itemView.address_type.text  = address.name
        holder.itemView.address_detail.text = address.road+", "+address.house+", "+address.sub_district+", "+address.district+", "+address.province





    }


    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}