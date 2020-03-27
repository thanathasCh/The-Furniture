package com.example.furnitureapp



import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.models.Categories
import kotlinx.android.synthetic.main.catergories_cell.view.*


class NewArrivalAdapter(val catergories: ArrayList<Categories>) : RecyclerView.Adapter<NewArrivalAdapter.CustomViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NewArrivalAdapter.CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.catergories_cell, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return catergories.size
    }

    override fun onBindViewHolder(holder: NewArrivalAdapter.CustomViewHolder, position: Int) {
        val catergory = catergories[position]

        holder.itemView.item_name.text = catergory.name
        holder.itemView.item_icon.setImageResource(catergory.image)
        e("Item are:", holder.itemView.item_name.toString())

    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}