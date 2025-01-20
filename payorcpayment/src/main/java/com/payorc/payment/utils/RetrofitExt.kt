package com.payorc.payment.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.payorc.payment.model.PayOrcError
import okhttp3.ResponseBody

fun ResponseBody.errorMessage(): String {
    val errorResponse: PayOrcError? =
        Gson().fromJson(charStream(), object : TypeToken<PayOrcError>() {}.type)
    return errorResponse?.message ?: "API Failed"
}