package com.example.furnitureapp.views.user

import android.os.Build
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furnitureapp.R
import com.example.furnitureapp.models.TransactionViewModel
import kotlinx.android.synthetic.main.parent_purchase_list.view.*
import kotlinx.android.synthetic.main.user_purchaselist_cell.view.*


class ParentPurchaseAdapter(
    val transactions: ArrayList<TransactionViewModel>,
    val context: UserPurchaseListFragment
) : RecyclerView.Adapter<ParentPurchaseAdapter.CustomViewHolder>() {

    var purchaseList = UserPurchaseListFragment


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.parent_purchase_list, parent, false)
        return CustomViewHolder(
            cellForRow
        )
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var childAdapter: UserPurchaseListAdapter
        val transaction = transactions[position]
        var dateList =  transaction.PaidAt.toString().split(" ")
        purchaseList.date = dateList[0]+" "+dateList[1]+", "+(dateList[2].toInt()+ 4)+"."
        holder.itemView.total_amount.text  = transaction.TotalAmount.toString()
        holder.itemView.date_purchase.text = dateList[0]+" "+dateList[1]+", "+dateList[2]+"."
        val recyclerView = holder.itemView.child_purchaselist
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL,true)
        childAdapter = UserPurchaseListAdapter(transaction.TransactionItems,context)
        recyclerView.adapter = childAdapter

    }


    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}