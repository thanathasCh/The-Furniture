package com.example.furnitureapp.BrowseItem


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.Categories.CategoriesFragment
import com.example.furnitureapp.Catergories
import com.example.furnitureapp.ClickEventHandler
import com.example.furnitureapp.R


class BrowseAdapter(
    val catergories: ArrayList<Catergories>,
    val context: BrowseItemFragment
) : RecyclerView.Adapter<BrowseAdapter.CustomViewHolder>() {

    private val clickHandler: ClickEventHandler = context as ClickEventHandler

    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.browse_cell, parent, false)
        return CustomViewHolder(
            cellForRow
        ).also {
            cellForRow.setOnClickListener { clickHandler.forwardClick(it) }
        }
    }

    override fun getItemCount(): Int {
        return catergories.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
// TODO set the all the detail according to id name

    }


    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}