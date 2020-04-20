package com.example.furnitureapp.data.api

import android.util.Log
import com.example.furnitureapp.MainActivity
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.models.User
import com.example.furnitureapp.models.UserViewMode
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class UserApi(private val db: CollectionReference = FirebaseFirestore.getInstance().collection("Users")) {
    fun isExist(userName: String, hashedPassword: String, callback: (Boolean) -> Unit) {
        db.whereEqualTo("UserName", userName)
            .whereEqualTo("Password", hashedPassword)
            .get().addOnCompleteListener {
                if (it.isSuccessful && it.result != null && !it.result!!.isEmpty) {
                    for (item in it.result!!) {
                        val user = item.toObject(UserViewMode::class.java)
                        user.Id = item.id
                        UserSharedPreference(MainActivity.mainThis).saveUser(user)
                        callback(true)
                    }
                } else {
                    callback(false)
                }
            }
    }

    fun createAccount(user: UserViewMode, callback: (Boolean) -> Unit) {
        db.add(user.toMap())
            .addOnSuccessListener {
                Log.d("DEBUG", "DONE WITH ${it.id}")
                callback(true)
            }
            .addOnFailureListener {
                Log.d("DEBUG", it.toString())
                callback(false)
            }
    }

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

    fun updateAddress() {}

}