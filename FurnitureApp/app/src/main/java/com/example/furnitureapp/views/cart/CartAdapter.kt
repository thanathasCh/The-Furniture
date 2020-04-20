package com.example.furnitureapp.views.cart


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furnitureapp.R
import com.example.furnitureapp.models.ProductViewModel
import kotlinx.android.synthetic.main.browse_cell.view.code
import kotlinx.android.synthetic.main.browse_cell.view.item_img
import kotlinx.android.synthetic.main.browse_cell.view.name
import kotlinx.android.synthetic.main.browse_cell.view.price
import kotlinx.android.synthetic.main.cart_cell.view.*


class CartAdapter(
    val product: ArrayList<ProductViewModel>,
    val context: CartFragment
) : RecyclerView.Adapter<CartAdapter.CustomViewHolder>() {


    //    private val clickHandler: ClickEventHandler = context
//    var bundle = Bundle()
    var selectProductCode = ArrayList<String>()
    var selectProductAmount = ArrayList<Int>()
    var selectProudctPosition = ArrayList<ProductViewModel>()


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

    fun removeAt(selectProduct:ProductViewModel) {
        product.remove(selectProduct)
        notifyDataSetChanged()
//        notifyItemRemoved(selectProduct)
    }

    override fun getItemCount(): Int {
        return product.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val product = product[position]
        var currentAmount = 1
        holder.itemView.name.text = product.Name
        holder.itemView.code.text = product.Code
        holder.itemView.price.text = product.Price.toString()
        Glide.with(context)
            .load(product.ImageUrls[0])
            .placeholder(R.drawable.loading)
            .into(holder.itemView.item_img)
        var select = true


        //Increase And Decrease Operation
        if(product.ProductStock == currentAmount){
            holder.itemView.plus.isEnabled = false
        }else if(1 == currentAmount){
            holder.itemView.minus.isEnabled = false
        }

        holder.itemView.plus.setOnClickListener {
            if(product.ProductStock == currentAmount){
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

        }



        holder.itemView.select.setOnClickListener {
            if (select) {
                holder.itemView.select.setBackgroundResource(R.drawable.black_cell)
                selectProductCode.add(product.Id.toString())
//                var currentAmount = holder.itemView.quantity
                selectProductAmount.add(currentAmount)
                selectProudctPosition.add(product)
                select = false
            } else {
                holder.itemView.select.setBackgroundResource(R.drawable.border)
                selectProductCode.remove(product.Id)
                selectProductAmount.remove(currentAmount)
                selectProudctPosition.remove(product)
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