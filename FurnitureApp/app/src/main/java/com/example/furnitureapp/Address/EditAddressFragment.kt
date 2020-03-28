package com.example.furnitureapp.Address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.furnitureapp.Communicator
import com.example.furnitureapp.R

/**
 * A simple [Fragment] subclass.
 */
class EditAddressFragment : Fragment(),Communicator {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_address, container, false)
    }

    override fun clickListener(holder: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
