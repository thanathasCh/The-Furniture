package com.example.furnitureapp.views.purchase


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furnitureapp.R
import com.example.furnitureapp.models.CartViewModel
import kotlinx.android.synthetic.main.purchase_cell.view.*


class ConfirmPurchaseAdapter(
    val product: ArrayList<CartViewModel>,
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
        val cart = product[position]
        holder.itemView.con_name.text = cart.Product.Name
        holder.itemView.con_code.text = cart.Product.Code
        holder.itemView.con_price.text = cart.Product.Price.toString()
        holder.itemView.con_amount.text = context.getString(R.string.x_quantity, cart.Quantity)
        holder.itemView.con_total.text = (cart.Product.Price*cart.Quantity).toString()
        Glide.with(context)
            .load(cart.Product.ImageUrls[0])
            .placeholder(R.drawable.loading)
            .into(holder.itemView.con_img)
    }


    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}