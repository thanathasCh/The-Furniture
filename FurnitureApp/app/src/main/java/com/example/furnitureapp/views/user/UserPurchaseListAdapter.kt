package com.example.furnitureapp.views.user

import android.os.Build
import com.example.furnitureapp.models.Product





import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.R
import com.example.furnitureapp.models.TransactionViewModel
import kotlinx.android.synthetic.main.user_purchaselist_cell.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class UserPurchaseListAdapter(
    val transactions: ArrayList<TransactionViewModel>,
    val context: UserPurchaseListFragment
) : RecyclerView.Adapter<UserPurchaseListAdapter.CustomViewHolder>() {



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
        return transactions.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val transaction = transactions[position]
        //Get Current Date
//        val current = LocalDateTime.now()
//        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
//        val formatted = current.format(formatter)
//        val day = formatted.substring(4,6)
//        val month = formatted.substring(0,3)

//        with (holder.itemView) {
//
//        }
//        holder.itemView.upurchase_name.text = transaction.name
//        holder.itemView.upurchase_code.text = transaction.code
//        holder.itemView.upurchase_quantity.text = transaction.available.toString()
//        holder.itemView.upurchase_price.text = (transaction.price*transaction.available).toString()
//        holder.itemView.upurchase_delivery.text = day + month
//        holder.itemView.purchase_list_image.setImageResource(transaction.image)

        val check = false
//        holder.itemView.setOnClickListener{
//            clickHandler.clickListener(it)
//        }

    }


    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}