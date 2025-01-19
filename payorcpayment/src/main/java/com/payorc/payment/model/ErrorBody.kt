package com.payorc.payment.model

import com.google.gson.annotations.SerializedName


data class ErrorBody(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)