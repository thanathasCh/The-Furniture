package com.example.furnitureapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)
fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }
fun String.encrypt() = toByteArray().toHexString()