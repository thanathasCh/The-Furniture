package com.example.furnitureapp.Categories



import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.Catergories
import com.example.furnitureapp.ClickEventHandler
import com.example.furnitureapp.R
import kotlinx.android.synthetic.main.catergories_cell.view.*


class CategoriesAdapter(
    val catergories: ArrayList<Catergories>,
    val context: CategoriesFragment
) : RecyclerView.Adapter<CategoriesAdapter.CustomViewHolder>() {

    private val clickHandler: ClickEventHandler = context as ClickEventHandler

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.catergories_cell, parent, false)
        return CustomViewHolder(
            cellForRow
        ).also {
            cellForRow.setOnClickListener{clickHandler.forwardClick(it)}
        }
    }

    override fun getItemCount(): Int {
        return catergories.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val catergory = catergories[position]
        holder.itemView.item_name.text = catergory.name
        holder.itemView.item_icon.setImageResource(catergory.image)
        e("Item are:", holder.itemView.item_name.toString())


    }


    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


}