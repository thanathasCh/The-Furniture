package com.example.furnitureapp.views.user

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furnitureapp.R
import com.example.furnitureapp.models.ProductViewModel
import com.example.furnitureapp.models.TransactionItemViewModel
import com.example.furnitureapp.models.TransactionViewModel
import kotlinx.android.synthetic.main.user_purchaselist_cell.view.*



class UserPurchaseListAdapter(
    val product: ArrayList<TransactionItemViewModel>,
    val context: UserPurchaseListFragment
) : RecyclerView.Adapter<UserPurchaseListAdapter.CustomViewHolder>() {


    var purchaseList = UserPurchaseListFragment

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.user_purchaselist_cell, parent, false)
        return CustomViewHolder(
            cellForRow)
//        ).also {
//            cellForRow.setOnClickListener{clickHandler.forwardClick(it,"hello")}
//        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val products = product[position]
        //Get Current Date
//        val current = LocalDateTime.now()
//        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
//        val formatted = current.format(formatter)
//        val day = formatted.substring(4,6)
//        val month = formatted.substring(0,3)

//        with (holder.itemView) {
//
//        }
        holder.itemView.upurchase_name.text = products.Product.Name
        holder.itemView.upurchase_code.text = products.Product.Code
        holder.itemView.upurchase_quantity.text = products.Quantity.toString()
        holder.itemView.upurchase_price.text = products.TotalAmount.toString()
        holder.itemView.upurchase_delivery.text = purchaseList.date
        Glide.with(context)
            .load(products.Product.ImageUrls[0])
            .placeholder(R.drawable.loading)
            .into(holder.itemView.purchase_list_image)
//        holder.itemView.purchase_list_image.setImageResource(transaction.image)

        val check = false
//        holder.itemView.setOnClickListener{
//            clickHandler.clickListener(it)
//        }

    }


    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}