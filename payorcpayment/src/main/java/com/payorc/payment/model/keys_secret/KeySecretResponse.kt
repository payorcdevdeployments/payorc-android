package com.payorc.payment.model.keys_secret

import com.google.gson.annotations.SerializedName


data class KeySecretResponse(
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)