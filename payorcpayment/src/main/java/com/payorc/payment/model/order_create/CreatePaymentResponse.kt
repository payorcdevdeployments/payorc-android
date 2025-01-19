package com.payorc.payment.model.order_create

import com.google.gson.annotations.SerializedName

data class CreatePaymentResponse(
    @SerializedName("amount")
    val amount: String?=null,
    @SerializedName("iframe_link")
    val iframeLink: String?=null,
    @SerializedName("m_order_id")
    val mOrderId: String?=null,
    @SerializedName("message")
    val message: String?=null,
    @SerializedName("order_creation_date")
    val orderCreationDate: String?=null,
    @SerializedName("p_order_id")
    val pOrderId: String?=null,
    @SerializedName("p_request_id")
    val pRequestId: String?=null,
    @SerializedName("payment_link")
    val paymentLink: String?=null,
    @SerializedName("status")
    val status: String?=null,
    @SerializedName("status_code")
    val statusCode: String?=null
)


