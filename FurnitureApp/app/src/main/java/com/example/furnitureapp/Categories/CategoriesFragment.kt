package com.example.furnitureapp.Categories


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.*
import com.example.furnitureapp.BrowseItem.BrowseItemFragment
import com.example.furnitureapp.models.Catergories


/**
 * A simple [Fragment] subclass.
 */
class CategoriesFragment : Fragment(),
    Communicator {

    var catergory = ArrayList<Catergories>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_categories, container, false)

        val back = view.findViewById<View>(R.id.back) as ImageView
        back.setOnClickListener{
            val home = HomeFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,home)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        catergory.add(
            Catergories(
                "Table",
                R.drawable.desk
            )
        )
        catergory.add(
            Catergories(
                "Chair",
                R.drawable.chair
            )
        )
        catergory.add(
            Catergories(
                "Matress",
                R.drawable.mattress
            )
        )
        catergory.add(
            Catergories(
                "Closet",
                R.drawable.closet
            )
        )
        catergory.add(
            Catergories(
                "Bed",
                R.drawable.bed
            )
        )
        catergory.add(
            Catergories(
                "More",
                R.drawable.more
            )
        )
        val catergoriesView = view.findViewById<RecyclerView>(R.id.recyclerCategories) as RecyclerView
        catergoriesView.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, true)
        catergoriesView.adapter =
            CategoriesAdapter(
                catergory,
                this
            )

        return view

    }



    override fun clickListener(holder: View) {
        val item = BrowseItemFragment()
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,item)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit() //To change body of created functions use File | Settings | File Templates.
    }


}
