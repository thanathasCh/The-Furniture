package com.example.furnitureapp.views.browseItem


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furnitureapp.interfaces.ClickEventHandler
import com.example.furnitureapp.R
import com.example.furnitureapp.models.ProductViewModel
import kotlinx.android.synthetic.main.browse_cell.view.*



class BrowseAdapter(
    val product: ArrayList<ProductViewModel>,
    val context: BrowseItemFragment
) : RecyclerView.Adapter<BrowseAdapter.CustomViewHolder>() {


    private val clickHandler: ClickEventHandler = context
    var bundle = Bundle()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.browse_cell, parent, false)

        return CustomViewHolder(
            cellForRow
        )
    }


    override fun getItemCount(): Int {
        return product.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val product = product[position]
        holder.itemView.name.text = product.Name
        holder.itemView.code.text = product.Code
        holder.itemView.size.text = product.Description
        holder.itemView.price.text = product.Price.toString()
        Glide.with(context)
            .load(product.ImageUrls[0])
            .placeholder(R.drawable.loading)
            .into(holder.itemView.item_img)

        holder.itemView.setOnClickListener {
            clickHandler.forwardClick(it, product)

        }


    }


    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}