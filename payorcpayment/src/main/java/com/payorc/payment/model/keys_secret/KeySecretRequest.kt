package com.payorc.payment.model.keys_secret

import com.google.gson.annotations.SerializedName


data class KeySecretRequest(
    @SerializedName("env")
    val env: String? = null,
    @SerializedName("merchant_key")
    val merchantKey: String? = null,
    @SerializedName("merchant_secret")
    val merchantSecret: String? = null
)