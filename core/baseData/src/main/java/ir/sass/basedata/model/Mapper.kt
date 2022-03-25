package ir.sass.basedata.model

import com.google.gson.Gson

fun <T>toJsonString(input : T) : String = Gson().toJson(input).toString()

inline fun <reified T>toReal(input : String) : T = Gson().fromJson(input,T::class.java)