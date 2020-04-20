package com.example.furnitureapp.views.user


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.furnitureapp.R
import com.example.furnitureapp.services.addressList
import com.example.furnitureapp.services.allUser
import com.example.furnitureapp.models.Address
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.models.User
import kotlinx.android.synthetic.main.fragment_create_new.view.*
import kotlinx.android.synthetic.main.fragment_create_new.view.female
import kotlinx.android.synthetic.main.fragment_create_new.view.male

/**
 * A simple [Fragment] subclass.
 */
class CreateNewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_new, container, false)

        val back = view.findViewById<View>(R.id.back_btn)
        val back_text = view.findViewById<View>(R.id.back)
        var isMale: Boolean? = null

        back.setOnClickListener{
            val login = LoginFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout,login)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.male.setOnClickListener {
            view.male.setBackgroundResource(R.drawable.border)
            isMale = true
            view.female.setBackgroundResource(R.drawable.grey_border)
        }
        view.female.setOnClickListener {
            view.female.setBackgroundResource(R.drawable.border)
            isMale = false
            view.male.setBackgroundResource(R.drawable.grey_border)
        }

        //Action Button
        view.create_new_create_btn.setOnClickListener{
            var success = 0
            val userName = view.user_name.text.toString()
            val password = view.password.text.toString()
            val firstName = view.first_name.text.toString()
            val lastName = view.last_name.text.toString()
            val email = view.email.text.toString()
            val phoneNumber = view.phone_number.text.toString()
            val house = view.create_new_house_number.text.toString()
            val road = view.create_new_road.text.toString()
            val subDistrict = view.create_new_subdistrict.text.toString()
            val district = view.create_new_district.text.toString()
            val province = view.create_new_province.text.toString()

            for (i in allUser){
                if (userName == i.UserName){
                    showAlert("User Name is Already Taken!")
                } else if (view.user_name.text.toString().isEmpty()){
                    showAlert("Please fill User Name")
                } else{
                    success += 1
                }
            }
            if (password.length < 8){
                showAlert("Password Must Be More Than 8 Character!")
            }else{
                if (view.password.text.toString() == view.reenter_password.text.toString()){
                    success += 1
                }else{
                    showAlert("Password is Not Match!")

                }
            }
            if (isMale?.equals(null)!!){
                showAlert("Please Select Gender!")
            } else{
                success += 1
            }
            if (firstName.isNotEmpty()){
                success += 1
            }
            if(lastName.isNotEmpty()){
                success += 1
            }
            if (email.isNotEmpty()){
                success += 1
            }
            if (phoneNumber.isNotEmpty()){
                success += 1
            }
            if (house.isNotEmpty()){
                success += 1
            }
            if (road.isNotEmpty()){
                success += 1
            }
            if (subDistrict.isNotEmpty()){
                success += 1
            }
            if (district.isNotEmpty()){
                success += 1
            }
            if (province.isNotEmpty()){
                success += 1
            }
            if (success  == 12){
                var id = 2
                var addressID = 1
                var gender = ""
                var newUser: User? = null
                var newAddress = ArrayList<Address>()
                var newPurchaseList = ArrayList<Product>()
                if (!isMale!!){
                    gender = "Female"
                }else{
                    gender = "Male"
                }
                addressList.add(Address("a$addressID",
                    "u$id","Home",firstName,road,house,subDistrict,district,province,true,phoneNumber))
                newUser = User(
                    "u$id",gender,firstName,lastName,userName,password,phoneNumber,email,
                    addressList,newPurchaseList)
                allUser.add(newUser)
                val builder = AlertDialog.Builder(this.activity)
                builder.setTitle("New User Have Been Created")
                builder.setPositiveButton("Login") { dialogInterface: DialogInterface?, i: Int ->
                    for(i in allUser){
                        e("All User", i.phoneNumber)
                    }
                    newAddress.clear()
                    val fragmentManager = activity!!.supportFragmentManager
                    fragmentManager.popBackStack()
                }
                builder.show()
            }
        }


        return view
    }
    fun showAlert(message:String){
        val builder = AlertDialog.Builder(this.activity)
        builder.setTitle(message)
        builder.setPositiveButton("Okay") { dialogInterface: DialogInterface?, i: Int ->
        }
        builder.show()
    }


}
