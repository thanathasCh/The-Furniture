package com.example.furnitureapp.views.address

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.example.furnitureapp.R
import com.example.furnitureapp.services.addressData
import com.example.furnitureapp.services.addressList
import com.example.furnitureapp.services.allUser
import com.example.furnitureapp.models.Address
import kotlinx.android.synthetic.main.fragment_edit_address.view.*

/**
 * A simple [Fragment] subclass.
 */
class EditAddressFragment : Fragment(){
    var currentIndex = 0
    var currentType = ""
    var isAdd = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_edit_address, container, false)
        var id = arguments?.getString("id")
        isAdd = arguments?.getBoolean("add")!!
        var back = view.findViewById<View>(R.id.edit_back) as ImageView
        var name = view.edit_name
        var phone = view.edit_phone
        var road = view.edit_road
        var house = view.edit_house
        var subDistrict = view.edit_subdistrict
        var district = view.edit_district
        var province = view.edit_province
        for (i in 0 until  addressData.size){
            if(addressData[i].id.equals(id)&& allUser[i].id.equals(
                    addressData[i].id)){
                currentIndex = i
                currentType = addressList[i].type.toString()
                name.setText(addressList[i].name)
                phone.setText(addressList[i].phoneNumber)
                road.setText(addressList[i].road)
                house.setText(addressList[i].house)
                subDistrict.setText(addressList[i].sub_district)
                district.setText(addressList[i].district)
                province.setText(addressList[i].province)
                if (addressList[i].type.equals("Office")){
                    view.edit_home.setBackgroundResource(R.drawable.grey_border)
                }else{
                    view.edit_office.setBackgroundResource(R.drawable.grey_border)
                }
            }
        }
        view.edit_home.setOnClickListener {
            currentType = "Home"
            view.edit_office.setBackgroundResource(R.drawable.grey_border)
            view.edit_home.setBackgroundResource(R.drawable.border)
        }
        view.edit_office.setOnClickListener {
            currentType = "Office"
            view.edit_home.setBackgroundResource(R.drawable.grey_border)
            view.edit_office.setBackgroundResource(R.drawable.border)
        }


        //Save Button
        view.edit_save.setOnClickListener {
            var  check = checkAddress(name,phone,road,house,subDistrict,district,province,currentType,isAdd)
            if (check){
                val fragmentManager = activity!!.supportFragmentManager
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle("Save ?")
                builder.setPositiveButton("Yes") { dialogInterface: DialogInterface?, i: Int ->
                    val fragmentManager = activity!!.supportFragmentManager
                    fragmentManager.popBackStack()
                }
                builder.setNegativeButton("No"){ dialogInterface: DialogInterface?, i: Int ->

                }
                builder.show()
            }else{
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle("Please Fill All the Information")
                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->  }
                builder.show()
            }
        }

        //Delete Button
        view.edit_delete.setOnClickListener {
            val builder = AlertDialog.Builder(this.activity)
            builder.setTitle("DELETE ?")
            builder.setPositiveButton("Yes") { dialogInterface: DialogInterface?, i: Int ->
                addressList.removeAt(currentIndex)
                val fragmentManager = activity!!.supportFragmentManager
                fragmentManager.popBackStack()
            }
            builder.setNegativeButton("No"){ dialogInterface: DialogInterface?, i: Int ->

            }
            builder.show()
        }

        //Add Button


        //Back Button
        back.setOnClickListener {
            isAdd = false
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.popBackStack()
        }



        return view
    }
    fun checkAddress(name:EditText,phone:EditText,road:EditText,house:EditText,subDistrict:EditText,district:EditText,province:EditText,currentType:String,isAdd:Boolean):Boolean{
        var check  = false
        if (isAdd){
            var currentID = 0
            if (addressList.size == 0){
                currentID = 0
            }else {
                currentID = addressList[addressList.size - 1].id?.substring(1, 2)?.toInt()?.plus(1)!!
            }
            var currentName = ""
            var currentPhone = ""
            var currentRoad = ""
            var currentHouse = ""
            var currentSubstrict = ""
            var currentDistrict = ""
            var currentProvince = ""
            if (name.text.toString() != "") {
                currentName = name.text.toString()
                check = true
            } else {
                check = false
            }
            if (phone.text.toString() != "") {
                currentPhone = phone.text.toString()
                check = true
            } else {
                check = false
            }
            if (road.text.toString() != "") {
                currentRoad = road.text.toString()
                check = true
            } else {
                check = false
            }
            if (house.text.toString() != "") {
                currentHouse= house.text.toString()
                check = true
            } else {
                check = false
            }
            if (subDistrict.text.toString() != "") {
                currentSubstrict = subDistrict.text.toString()
                check = true
            } else {
                check = false
            }
            if (district.text.toString() != "") {
                currentDistrict = district.text.toString()
                check = true
            } else {
                check = false
            }
            if (province.text.toString() != "") {
                currentProvince = province.text.toString()
                check = true
            } else {
                check = false
            }
            if (province.text.toString() != "") {

            } else {
                check = false
            }
            if (check){
                addressList.add(Address("a$currentID",
                    allUser[currentIndex].id!!,currentType,currentName,currentRoad,currentHouse,currentSubstrict,currentDistrict,currentProvince,false,currentPhone))
            }
        }else {
            if (name.text.toString() != "") {
                addressList[currentIndex].name = name.text.toString()
                check = true
            } else {
                check = false
            }
            if (phone.text.toString() != "") {
                addressList[currentIndex].phoneNumber = phone.text.toString()
                check = true
            } else {
                check = false
            }
            if (road.text.toString() != "") {
                addressList[currentIndex].road = road.text.toString()
                check = true
            } else {
                check = false
            }
            if (house.text.toString() != "") {
                addressList[currentIndex].house = house.text.toString()
                check = true
            } else {
                check = false
            }
            if (subDistrict.text.toString() != "") {
                addressList[currentIndex].sub_district = subDistrict.text.toString()
                check = true
            } else {
                check = false
            }
            if (district.text.toString() != "") {
                addressList[currentIndex].district = district.text.toString()
                check = true
            } else {
                check = false
            }
            if (province.text.toString() != "") {
                addressList[currentIndex].province = province.text.toString()
                check = true
            } else {
                check = false
            }
            addressList[currentIndex].type = currentType
        }
        return check
    }
}
