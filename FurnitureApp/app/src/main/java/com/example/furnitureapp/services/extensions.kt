package com.example.furnitureapp.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.regex.Matcher
import java.util.regex.Pattern


inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)
fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }
fun String.encrypt() = toByteArray().toHexString()

fun isEmailValid(email: String?): Boolean {
    val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(email)
    return matcher.matches()
}