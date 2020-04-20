package com.example.furnitureapp.views.category



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.furnitureapp.interfaces.Communicator
import com.example.furnitureapp.R
import com.example.furnitureapp.models.CategoryViewModel
import kotlinx.android.synthetic.main.catergories_cell.view.*


class CategoriesAdapter(
    val catergories: ArrayList<CategoryViewModel>,
    val context: CategoriesFragment
) : RecyclerView.Adapter<CategoriesAdapter.CustomViewHolder>() {

    private val clickHandler: Communicator = context


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.catergories_cell, parent, false)
        return CustomViewHolder (
            cellForRow)
//        ).also {
//            cellForRow.setOnClickListener{clickHandler.forwardClick(it,"hello")}
//        }
    }

    override fun getItemCount(): Int {
        return catergories.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val category = catergories[position]
        holder.itemView.item_name.text = category.Name
        Glide.with(context)
            .load(category.ImageUrl)
            .placeholder(R.drawable.loading)
            .into(holder.itemView.item_icon)
        val check = false
        holder.itemView.setOnClickListener{
            clickHandler.clickListener(it,  category.Id)
        }

    }


    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}