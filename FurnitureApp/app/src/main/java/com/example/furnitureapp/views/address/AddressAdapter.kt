package com.example.furnitureapp.views.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.interfaces.Communicator
import com.example.furnitureapp.R
import com.example.furnitureapp.models.Address
import com.example.furnitureapp.models.AddressViewModel
import kotlinx.android.synthetic.main.address_cell.view.*

class AddressAdapter(
    val address: ArrayList<AddressViewModel>,
    val context: AddressFragment
) : RecyclerView.Adapter<AddressAdapter.CustomViewHolder>() {


    var bundle = Bundle()
    private val clickHandler: Communicator = context



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
        holder.itemView.address_name.text  = address.Name
        holder.itemView.address_phone_number.text = address.TelephoneNumber
        holder.itemView.address_detail.text = address.getFullAddress()
        holder.itemView.btn_edit.setOnClickListener {
            clickHandler.clickWithDataTransfer(it, address.Id ?: "")
        }
        holder.itemView.address_name.setOnClickListener {
            clickHandler.clickToSelect(it,address.Id ?: "")
        }
    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}