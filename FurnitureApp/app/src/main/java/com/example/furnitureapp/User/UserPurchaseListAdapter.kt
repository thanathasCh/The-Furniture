package com.example.furnitureapp.User

import android.os.Build
import com.example.furnitureapp.models.Product





import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.models.Categories
import com.example.furnitureapp.Communicator
import com.example.furnitureapp.R
import kotlinx.android.synthetic.main.catergories_cell.view.*
import kotlinx.android.synthetic.main.fragment_confirm_purchase.view.*
import kotlinx.android.synthetic.main.user_puchaselist_cell.view.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class UserPurchaseListAdapter(
    val purchaseList: ArrayList<Product>,
    val context: UserPurchaseListFragment
) : RecyclerView.Adapter<UserPurchaseListAdapter.CustomViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.user_puchaselist_cell, parent, false)
        return CustomViewHolder(
            cellForRow)
//        ).also {
//            cellForRow.setOnClickListener{clickHandler.forwardClick(it,"hello")}
//        }
    }

    override fun getItemCount(): Int {
        return purchaseList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val product = purchaseList[position]
        //Get Current Date
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)
        val day = formatted.substring(4,6)
        val month = formatted.substring(0,3)
        e("Date", day)
        e("Month", month)
        holder.itemView.upurchase_name.text = product.name
        holder.itemView.upurchase_code.text = product.code
        holder.itemView.upurchase_quantity.text = product.available.toString()
        holder.itemView.upurchase_price.text = (product.price*product.available).toString()
        holder.itemView.upurchase_delivery.text = day +"-"+ (day.toInt()+2) +" "+ month
        holder.itemView.purchase_list_image.setImageResource(product.image)

        val check = false
//        holder.itemView.setOnClickListener{
//            clickHandler.clickListener(it)
//        }

    }


    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}