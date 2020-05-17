package com.example.furnitureapp.views.arrival



import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.R
import com.example.furnitureapp.models.CategoryViewModel
import kotlinx.android.synthetic.main.catergories_cell.view.*


class NewArrivalAdapter(private val categories: ArrayList<CategoryViewModel>) : RecyclerView.Adapter<NewArrivalAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.catergories_cell, parent, false)
        return CustomViewHolder(
            cellForRow
        )
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val category = categories[position]

        holder.itemView.item_name.text = category.Name
//        holder.itemView.item_icon.setImageResource(category.image)
        e("Item are:", holder.itemView.item_name.toString())
    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}