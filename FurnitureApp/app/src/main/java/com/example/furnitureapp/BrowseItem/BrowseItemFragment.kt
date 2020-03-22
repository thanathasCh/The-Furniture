package com.example.furnitureapp.BrowseItem


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furnitureapp.*
import com.example.furnitureapp.Categories.CategoriesFragment
import com.example.furnitureapp.Product.ProductFragment
import com.example.furnitureapp.models.Product

/**
 * A simple [Fragment] subclass.
 */
class BrowseItemFragment : Fragment(),
    ClickEventHandler {

    var product = ArrayList<Product>()
    var singleton = Singleton()

    protected lateinit var rootView: View
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_browse_item, container, false)

        val back = view.findViewById<View>(R.id.back_btn_browse)
        back.setOnClickListener{
            val categories =
                CategoriesFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,categories)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
//        product.add(
//            Product(
//                "Sofa Table",
//                "100 x 20 x 30",
//                "A02",
//                1500.0F,
//                R.drawable.table_product,
//                "Glass, Wood",
//                4
//            )
//        )
//        product.add(
//            Product(
//                "Desk",
//                "150 x 65 x 50",
//                "A01",
//                2000.0F,
//                R.drawable.desk_product,
//                "Wood",
//                2
//            )
//        )
//
////        TODO bind data in to recycler view
        val listOfProduct = view.findViewById<RecyclerView>(R.id.recyclerview_list_of_product) as RecyclerView
        listOfProduct.layoutManager = LinearLayoutManager(activity,  LinearLayoutManager.VERTICAL, true)
        listOfProduct.adapter =
            BrowseAdapter(
                singleton.createMockUp(),
                this
            )

        return view
    }


    override fun forwardClick(
        holder: View,
        id: String,
        name: String,
        size: String,
        code: String,
        price: Float,
        image: Int,
        material: String,
        available: Int
    ) {
        val bundle = Bundle()
        bundle.putString("id",id)
        bundle.putString("name",name)
        bundle.putString("size",size)
        bundle.putString("code",code)
        bundle.putFloat("price",price)
        bundle.putInt("image",image)
        bundle.putString("material", material)
        bundle.putInt("available", available)
        val product = ProductFragment()
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        product.arguments = bundle
        fragmentTransaction.replace(R.id.frame_layout,product)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
