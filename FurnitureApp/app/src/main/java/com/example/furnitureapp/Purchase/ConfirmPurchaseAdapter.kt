package com.example.furnitureapp.Purchase


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.R
import kotlinx.android.synthetic.main.browse_cell.view.*
import kotlinx.android.synthetic.main.purchase_cell.view.*


class ConfirmPurchaseAdapter(
    val product: ArrayList<Product>,
    val context: ConfirmPurchaseFragment
) : RecyclerView.Adapter<ConfirmPurchaseAdapter.CustomViewHolder>() {

    var bundle = Bundle()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.purchase_cell, parent, false)

        return CustomViewHolder(
            cellForRow
        )
    }


    override fun getItemCount(): Int {
        return product.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val product = product[position]
        holder.itemView.con_name.text = product.name
        holder.itemView.con_code.text = product.code
        holder.itemView.con_price.text = product.price.toString()
        holder.itemView.con_amount.text = "x"+product.available
        holder.itemView.con_total.text = (product.price*product.available).toString()
        holder.itemView.con_img.setImageResource(product.image)


    }


    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}