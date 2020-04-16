package com.example.furnitureapp.data.api

import android.util.Log
import com.example.furnitureapp.MainActivity
import com.example.furnitureapp.data.local.UserSharedPreference
import com.example.furnitureapp.models.User
import com.example.furnitureapp.models.UserViewMode
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.security.auth.callback.Callback

class UserApi(private val db: CollectionReference = FirebaseFirestore.getInstance().collection("Users")) {
    fun isExist(userName: String, hashedPassword: String, callback: (Boolean) -> Unit) {
        db.whereEqualTo("UserName", userName)
            .whereEqualTo("Password", hashedPassword)
            .get().addOnCompleteListener {
                if (it.isSuccessful && it.result != null && !it.result!!.isEmpty) {
                    val user = it.result!!.toObjects(UserViewMode::class.java)
                    UserSharedPreference(MainActivity.mainThis).saveUser(user[0])
                    callback(true)
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
}