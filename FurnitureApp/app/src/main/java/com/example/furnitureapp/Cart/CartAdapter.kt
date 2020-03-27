package com.example.furnitureapp.Cart


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.R
import kotlinx.android.synthetic.main.browse_cell.view.*
import kotlinx.android.synthetic.main.browse_cell.view.code
import kotlinx.android.synthetic.main.browse_cell.view.item_img
import kotlinx.android.synthetic.main.browse_cell.view.name
import kotlinx.android.synthetic.main.browse_cell.view.price
import kotlinx.android.synthetic.main.cart_cell.view.*


class CartAdapter(
    val product: ArrayList<Product>,
    val context: CartFragment
) : RecyclerView.Adapter<CartAdapter.CustomViewHolder>() {


//    private val clickHandler: ClickEventHandler = context
//    var bundle = Bundle()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.cart_cell, parent, false)
        return CustomViewHolder(
            cellForRow
        )
    }


    override fun getItemCount(): Int {
        return product.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val product = product[position]
        holder.itemView.name.text = product.name
        holder.itemView.code.text = product.code
        holder.itemView.price.text = product.price.toString()
        holder.itemView.item_img.setImageResource(product.image)
        var check = true
        holder.itemView.select.setOnClickListener {
            if (check){
                holder.itemView.select.setBackgroundResource(R.drawable.black_cell)
                check = false
            }else{
                holder.itemView.select.setBackgroundResource(R.drawable.border)
                check = true

            }

        }

//        holder.itemView.setOnClickListener {
//            clickHandler.forwardClick(it,product.name.toString(),product.size.toString(),product.code.toString(),product.price,product.image,product.material.toString(),product.available)
//
//
//        }


    }


    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}