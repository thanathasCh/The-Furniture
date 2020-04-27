package com.example.furnitureapp.data.api

import android.util.Log
import com.example.furnitureapp.views.main.MainActivity
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.models.UserViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class UserApi(private val db: CollectionReference = FirebaseFirestore.getInstance().collection("Users")) {

    // check if the user with this password is existed or not
    fun isExist(userName: String, hashedPassword: String, callback: (Boolean) -> Unit) {
        db.whereEqualTo("Username", userName)
            .whereEqualTo("Password", hashedPassword)
            .get().addOnCompleteListener {
                if (it.isSuccessful && it.result != null && !it.result!!.isEmpty) {
                    for (item in it.result!!) {
                        val user = item.toObject(UserViewModel::class.java)
                        user.Id = item.id
                        UserSharedPreference(MainActivity.mainThis).saveUser(user)
                        callback(true)
                    }
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // check if the user name is already existed or not
    fun isDuplicated(userName: String, callback: (Boolean) -> Unit) {
        db.whereEqualTo("UserName", userName)
            .get()
            .addOnCompleteListener {
                callback((it.isSuccessful && it.result != null && !it.result!!.isEmpty))
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // create accunt
    fun createAccount(user: UserViewModel, callback: (Boolean) -> Unit) {
        db.add(user.toMap())
            .addOnSuccessListener {

                getUser(it.id ?: "") {
                    callback(true)
                }
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // update user's name
    fun updateName(firstName: String, lastName: String, callback: (Boolean) -> Unit) {
        val userPreference = UserSharedPreference(MainActivity.mainThis)
        val user = userPreference.retrieveUser()

        user.FirstName = firstName
        user.LastName = lastName

        db.document(user.Id ?: "").update("FirstName", firstName)
        db.document(user.Id ?: "").update("LastName", lastName)

        userPreference.saveUser(user)
        callback(true)
    }

    // update user's password
    fun updatePassword(newPassword: String, callback: (Boolean) -> Unit) {
        val userPreference = UserSharedPreference(MainActivity.mainThis)
        val user = userPreference.retrieveUser()

        user.Password = newPassword

        db.document(user.Id ?: "").update("Password", newPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    userPreference.saveUser(user)
                    callback(true)
                } else {
                     callback(false)
                }
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // get user from db by user id
    fun getUser(userId: String, callback: (UserViewModel) -> Unit) {
        db.document(userId)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = it.result!!.toObject(UserViewModel::class.java) ?: UserViewModel()
                    user.Id = userId
                    UserSharedPreference(MainActivity.mainThis).saveUser(user)
                    callback(user)
                }
            }
            .addOnFailureListener {
                callback(UserSharedPreference(MainActivity.mainThis).retrieveUser())
            }
    }

    //update telephone number
    fun updateTelephoneNumber(telephoneNumber: String, callback: (Boolean) -> Unit) {
        val userPreference = UserSharedPreference(MainActivity.mainThis)
        val user = userPreference.retrieveUser()

        user.TelephoneNumber = telephoneNumber

        db.document(user.Id ?: "").update("TelephoneNumber", telephoneNumber)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    userPreference.saveUser(user)
                    callback(true)
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener {
                callback(false)
            }

    }

    // update user's email
    fun updateEmail(email: String, callback: (Boolean) -> Unit) {
        val userPreference = UserSharedPreference(MainActivity.mainThis)
        val user = userPreference.retrieveUser()

        user.Email = email

        db.document(user.Id ?: "").update("Email", email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    userPreference.saveUser(user)
                    callback(true)
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}