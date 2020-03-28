package com.example.furnitureapp.Cart


import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.R
import kotlinx.android.synthetic.main.browse_cell.view.code
import kotlinx.android.synthetic.main.browse_cell.view.item_img
import kotlinx.android.synthetic.main.browse_cell.view.name
import kotlinx.android.synthetic.main.browse_cell.view.price
import kotlinx.android.synthetic.main.cart_cell.view.*
import kotlinx.android.synthetic.main.fragment_purchase.view.*


class CartAdapter(
    val product: ArrayList<Product>,
    val context: CartFragment
) : RecyclerView.Adapter<CartAdapter.CustomViewHolder>() {


    //    private val clickHandler: ClickEventHandler = context
//    var bundle = Bundle()
    var selectProductCode = ArrayList<String>()
    var selectProductAmount = ArrayList<Int>()


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
        var currentAmount = 1
        holder.itemView.name.text = product.name
        holder.itemView.code.text = product.code
        holder.itemView.price.text = product.price.toString()
        holder.itemView.item_img.setImageResource(product.image)
        var select = true


        //Increase And Decrease Operation
        if(product.available == currentAmount){
            holder.itemView.plus.isEnabled = false
        }else if(1 == currentAmount){
            holder.itemView.minus.isEnabled = false
        }

        holder.itemView.plus.setOnClickListener {
            if(product.available == currentAmount){
                holder.itemView.plus.isEnabled = false
            }else{
                currentAmount += 1
                holder.itemView.quantity.setText(currentAmount.toString())
                holder.itemView.minus.isEnabled = true
            }
        }
        holder.itemView.minus.setOnClickListener {
            if(1 == currentAmount){
                holder.itemView.minus.isEnabled = false
            }else{
                currentAmount -= 1
                holder.itemView.quantity.setText(currentAmount.toString())
                holder.itemView.plus.isEnabled = true
            }

        }




        holder.itemView.select.setOnClickListener {
            if (select) {
                holder.itemView.select.setBackgroundResource(R.drawable.black_cell)
                selectProductCode.add(product.id.toString())
//                var currentAmount = holder.itemView.quantity
                selectProductAmount.add(currentAmount)
                select = false
            } else {
                holder.itemView.select.setBackgroundResource(R.drawable.border)
                selectProductCode.remove(product.id)
                selectProductAmount.remove(currentAmount)
                select = true
            }
//            e("product code: ", selectProductCode.toString())
//            e("product amount: ", selectProductAmount.toString())
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