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
import com.example.furnitureapp.data.api.UserApi
import com.example.furnitureapp.models.Address
import com.example.furnitureapp.models.Product
import com.example.furnitureapp.models.User
import com.example.furnitureapp.models.UserViewModel
import com.example.furnitureapp.services.*
import com.example.furnitureapp.views.main.MainActivity
import kotlinx.android.synthetic.main.fragment_confirm_purchase.*
import kotlinx.android.synthetic.main.fragment_create_new.view.*
import kotlinx.android.synthetic.main.fragment_create_new.view.female
import kotlinx.android.synthetic.main.fragment_create_new.view.male
import java.util.*

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
            MainActivity.mainSrl.isRefreshing = true
            val userName = view.user_name.text.toString()
            val password = view.password.text.toString().encrypt()
            val confirmPassowrd = view.reenter_password.text.toString().encrypt()
            val passwordLength = view.password.text.toString().length
            val firstName = view.first_name.text.toString()
            val lastName = view.last_name.text.toString()
            val email = view.email.text.toString()
            val telephoneNumber = view.phone_number.text.toString()
            val house = view.create_new_house_number.text.toString()
            val road = view.create_new_road.text.toString()
            val subDistrict = view.create_new_subdistrict.text.toString()
            val district = view.create_new_district.text.toString()
            val province = view.create_new_province.text.toString()

            val now = Date()
            val user = UserViewModel (
                "",
                firstName,
                lastName,
                0,
                userName,
                password,
                email,
                telephoneNumber,
                "",
                now,
                now
            )

            val alertBuilder = AlertBuilder()
            val userApi = UserApi()

            if (checkUser(user, passwordLength, confirmPassowrd)) {
                userApi.isDuplicated(user.UserName ?: "") {
                    if (!it) {
                        userApi.createAccount(user) { result ->
                            if (result) {
                                alertBuilder.showOkAlert(getString(R.string.account_created)) {
                                    val fragment = activity!!.supportFragmentManager
                                    fragment.popBackStack()
                                }
                            } else {
                                alertBuilder.showOkAlert(getString(R.string.error_occurred))
                            }
                            MainActivity.mainSrl.isRefreshing = false
                        }
                    } else {
                        alertBuilder.showOkAlert(getString(R.string.duplicate_username))
                        MainActivity.mainSrl.isRefreshing = true
                    }
                }
            } else {
                MainActivity.mainSrl.isRefreshing = false
            }
        }

        return view
    }

    private fun checkUser(user: UserViewModel, passwordLength: Int, confirmPassword: String): Boolean {
        val alertBuilder = AlertBuilder()
        if (user.UserName.isNullOrEmpty() || user.FirstName.isNullOrEmpty() || user.LastName.isNullOrEmpty()
            || user.Password.isNullOrEmpty() || user.Email.isNullOrEmpty() || user.TelephoneNumber.isNullOrEmpty()) {
            alertBuilder.showOkAlert(getString(R.string.require_information))
        } else if (passwordLength < 8) {
            alertBuilder.showOkAlert(getString(R.string.password_length))
        } else if (!user.Password.equals(confirmPassword)) {
            alertBuilder.showOkAlert(getString(R.string.password_not_match))
        } else if (!isEmailValid(user.Email)){
            alertBuilder.showOkAlert(getString(R.string.email_invalid))
        } else {
            return true
        }

        return false
    }
}
