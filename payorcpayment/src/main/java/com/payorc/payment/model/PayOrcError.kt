package com.payorc.payment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PayOrcError(
    @SerializedName("message") val message: String? = null,
    @SerializedName("status") val status: String? = null
) : Parcelable