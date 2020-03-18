package com.example.furnitureapp.api

import android.util.Log
import com.example.furnitureapp.models.CategoryViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class Examples {
    fun initDatabase() {
        val db = FirebaseFirestore.getInstance().collection("Categories")
        db.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (i in task.result!!) {
                    Log.e("DEBUG", i.toObject(CategoryViewModel::class.java).toString())
                }
            }
        }
    }

    fun addData() {

    }

    fun deleteData() {

    }

    fun readData() {
        val db = FirebaseFirestore.getInstance().collection("Categories").document("MexV1kafBFOO9CxC01Ev")
        db.get().addOnSuccessListener { document ->
            Log.d("DEBUG", document.data.toString())
        }
    }

    fun updateData() {

    }
}