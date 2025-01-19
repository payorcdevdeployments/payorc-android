package com.payorc.payment.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.payorc.payment.model.ErrorBody
import okhttp3.ResponseBody

fun ResponseBody.errorMessage():String{
    val errorResponse: ErrorBody? = Gson().fromJson(charStream(), object : TypeToken<ErrorBody>() {}.type)
    return errorResponse?.message?:"API Failed"
}