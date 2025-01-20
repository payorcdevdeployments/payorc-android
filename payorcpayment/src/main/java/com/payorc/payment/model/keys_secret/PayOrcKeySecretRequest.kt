package com.payorc.payment.model.keys_secret

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PayOrcKeySecretRequest(
    @SerializedName("env") val env: String? = null,
    @SerializedName("merchant_key") val merchantKey: String? = null,
    @SerializedName("merchant_secret") val merchantSecret: String? = null
) : Parcelable