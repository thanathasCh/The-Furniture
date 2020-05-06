package com.example.furnitureapp.views.cart


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furnitureapp.R
import com.example.furnitureapp.models.CartViewModel
import com.example.furnitureapp.models.ProductViewModel
import kotlinx.android.synthetic.main.browse_cell.view.code
import kotlinx.android.synthetic.main.browse_cell.view.item_img
import kotlinx.android.synthetic.main.browse_cell.view.name
import kotlinx.android.synthetic.main.browse_cell.view.price
import kotlinx.android.synthetic.main.cart_cell.view.*
import kotlin.math.log


class CartAdapter(
    val carts: ArrayList<CartViewModel>,
    val context: CartFragment
) : RecyclerView.Adapter<CartAdapter.CustomViewHolder>() {


    var selectProudctPosition = ArrayList<CartViewModel>()


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

    fun removeAt(cart:CartViewModel) {
        carts.remove(cart)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return carts.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val cart = carts[position]
        var currentAmount = 1
        var currentPrice = cart.Product.Price
        holder.itemView.name.text = cart.Product.Name
        holder.itemView.code.text = cart.Product.Code
        holder.itemView.price.text = cart.Product.Price.toString()
        holder.itemView.quantity.setText(cart.Quantity.toString())
//        Log.e("image url: ", cart.Product.ImageUrls[0])
        Glide.with(context)
            .load(cart.Product.ImageUrls[0])
            .placeholder(R.drawable.loading)
            .into(holder.itemView.item_img)
        var select = true

        //Increase And Decrease Operation
        if(cart.Product.ProductStock == currentAmount){
            holder.itemView.plus.isEnabled = false
        }else if(1 == currentAmount){
            holder.itemView.minus.isEnabled = false
        }

        holder.itemView.plus.setOnClickListener {
            if(cart.Product.ProductStock == currentAmount){
                holder.itemView.plus.isEnabled = false
            }else{
                currentAmount += 1
                currentPrice += cart.Product.Price
                holder.itemView.price.text = currentPrice.toString()
                holder.itemView.quantity.setText(currentAmount.toString())
                holder.itemView.minus.isEnabled = true
            }
        }

        holder.itemView.minus.setOnClickListener {
            if(1 == currentAmount){
                holder.itemView.minus.isEnabled = false
            }else{
                currentPrice -= cart.Product.Price
                currentAmount -= 1
                holder.itemView.price.text = currentPrice.toString()
                holder.itemView.quantity.setText(currentAmount.toString())
                holder.itemView.plus.isEnabled = true
            }

        }

        holder.itemView.select.setOnClickListener {
            if (select) {
                holder.itemView.select.setBackgroundResource(R.drawable.black_cell)
                var localCart = cart
                localCart.Quantity = currentAmount
                Log.e("current amount: ", localCart.Quantity.toString())
                selectProudctPosition.add(localCart)

                select = false
            } else {
                holder.itemView.select.setBackgroundResource(R.drawable.border)
                selectProudctPosition.remove(cart)
                select = true
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