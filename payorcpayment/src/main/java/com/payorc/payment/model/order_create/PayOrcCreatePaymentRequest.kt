package com.payorc.payment.model.order_create

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PayOrcCreatePaymentRequest(
    @SerializedName("data") val data: PaymentRequest
) : Parcelable

@Parcelize
data class PaymentRequest(
    @SerializedName("class") val classX: String? = null,
    @SerializedName("action") val action: String? = null,
    @SerializedName("capture_method") val captureMethod: String? = null,
    @SerializedName("order_details") val orderDetails: OrderDetails? = null,
    @SerializedName("customer_details") val customerDetails: CustomerDetails? = null,
    @SerializedName("billing_details") val billingDetails: BillingDetails? = null,
    @SerializedName("shipping_details") val shippingDetails: ShippingDetails? = null,
    @SerializedName("urls") val urls: Urls? = null,
    @SerializedName("parameters") val parameters: MutableList<Parameter>? = null,
    @SerializedName("custom_data") val customData: MutableList<CustomData>? = null,
    @SerializedName("payment_token") val paymentToken: String? = null
) : Parcelable

@Parcelize
data class BillingDetails(
    @SerializedName("address_line1") val addressLine1: String? = null,
    @SerializedName("address_line2") val addressLine2: String? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("province") val province: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("pin") val pin: String? = null
) : Parcelable

@Parcelize
data class CustomData(
    @SerializedName("alpha") val alpha: String? = null,
    @SerializedName("beta") val beta: String? = null,
    @SerializedName("delta") val delta: String? = null,
    @SerializedName("epsilon") val epsilon: String? = null,
    @SerializedName("gamma") val gamma: String? = null
) : Parcelable

@Parcelize
data class CustomerDetails(
    @SerializedName("m_customer_id") val mCustomerId: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("mobile") val mobile: String? = null,
    @SerializedName("code") val code: String? = null
) : Parcelable

@Parcelize
data class OrderDetails(
    @SerializedName("m_order_id") val mOrderId: String? = null,
    @SerializedName("amount") val amount: String? = null,
    @SerializedName("convenience_fee") val convenienceFee: String? = null,
    @SerializedName("quantity") val quantity: String? = null,
    @SerializedName("currency") val currency: String? = null,
    @SerializedName("description") val description: String? = null
) : Parcelable

@Parcelize
data class Parameter(
    @SerializedName("alpha") val alpha: String? = null,
    @SerializedName("beta") val beta: String? = null,
    @SerializedName("delta") val delta: String? = null,
    @SerializedName("epsilon") val epsilon: String? = null,
    @SerializedName("gamma") val gamma: String? = null
) : Parcelable

@Parcelize
data class ShippingDetails(
    @SerializedName("address_line1") val addressLine1: String? = null,
    @SerializedName("address_line2") val addressLine2: String? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("location_pin") val locationPin: String? = null,
    @SerializedName("pin") val pin: String? = null,
    @SerializedName("province") val province: String? = null,
    @SerializedName("shipping_amount") val shippingAmount: String? = null,
    @SerializedName("shipping_code") val shippingCode: String? = null,
    @SerializedName("shipping_currency") val shippingCurrency: String? = null,
    @SerializedName("shipping_email") val shippingEmail: String? = null,
    @SerializedName("shipping_mobile") val shippingMobile: String? = null,
    @SerializedName("shipping_name") val shippingName: String? = null
) : Parcelable

@Parcelize
data class Urls(
    @SerializedName("cancel") val cancel: String? = null,
    @SerializedName("failure") val failure: String? = null,
    @SerializedName("success") val success: String? = null
) : Parcelable




