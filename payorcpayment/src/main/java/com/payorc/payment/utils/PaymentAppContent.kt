package com.payorc.payment.utils

import com.google.gson.Gson

object PayOrcApiContent {
    const val KEYS_SECRET = "api/v1/check/keys-secret"
    const val CREATE_ORDER = "api/v1/sdk/orders/create"
    const val TRANSACTION_DETAILS = "api/v1/sdk/orders/transaction-details"
}

object PayOrcConstants {
    const val KEY_CREATE_ORDER = "KEY_CREATE_ORDER"
    const val PAY_ORC_PAYMENT_RESULT = "PAYMENT_RESULT"
    const val PAYMENT_RESULT_STATUS = "PAYMENT_RESULT_STATUS"
    const val PAYMENT_RESULT_DATA = "PAYMENT_RESULT_DATA"
    const val PAYMENT_ERROR_MESSAGE = "PAYMENT_ERROR_MESSAGE"
}

inline fun <reified T> String.jsonToGSON(): T {
    return Gson().fromJson(this, T::class.java)
}