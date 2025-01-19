package com.payorc.payment.model.order_create

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class CreatePaymentRequest(
    @SerializedName("data")
    val data: PaymentRequest
)

data class PaymentRequest(
    @SerializedName("action")
    val action: String?=null,
    @SerializedName("billing_details")
    val billingDetails: BillingDetails?=null,
    @SerializedName("capture_method")
    val captureMethod: String?=null,
    @SerializedName("class")
    val classX: String?=null,
    @SerializedName("custom_data")
    val customData: List<CustomData>?=null,
    @SerializedName("customer_details")
    val customerDetails: CustomerDetails?=null,
    @SerializedName("order_details")
    val orderDetails: OrderDetails?=null,
    @SerializedName("parameters")
    val parameters: List<Parameter>?=null,
    @SerializedName("payment_token")
    val paymentToken: String?=null,
    @SerializedName("shipping_details")
    val shippingDetails: ShippingDetails?=null,
    @SerializedName("urls")
    val urls: Urls?=null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(BillingDetails::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(CustomData),
        parcel.readParcelable(CustomerDetails::class.java.classLoader),
        parcel.readParcelable(OrderDetails::class.java.classLoader),
        parcel.createTypedArrayList(Parameter),
        parcel.readString(),
        parcel.readParcelable(ShippingDetails::class.java.classLoader),
        parcel.readParcelable(Urls::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(action)
        parcel.writeParcelable(billingDetails, flags)
        parcel.writeString(captureMethod)
        parcel.writeString(classX)
        parcel.writeTypedList(customData)
        parcel.writeParcelable(customerDetails, flags)
        parcel.writeParcelable(orderDetails, flags)
        parcel.writeTypedList(parameters)
        parcel.writeString(paymentToken)
        parcel.writeParcelable(shippingDetails, flags)
        parcel.writeParcelable(urls, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PaymentRequest> {
        override fun createFromParcel(parcel: Parcel): PaymentRequest {
            return PaymentRequest(parcel)
        }

        override fun newArray(size: Int): Array<PaymentRequest?> {
            return arrayOfNulls(size)
        }
    }
}


data class BillingDetails(
    @SerializedName("address_line1")
    val addressLine1: String?=null,
    @SerializedName("address_line2")
    val addressLine2: String?=null,
    @SerializedName("city")
    val city: String?=null,
    @SerializedName("country")
    val country: String?=null,
    @SerializedName("pin")
    val pin: String?=null,
    @SerializedName("province")
    val province: String?=null
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(addressLine1)
        parcel.writeString(addressLine2)
        parcel.writeString(city)
        parcel.writeString(country)
        parcel.writeString(pin)
        parcel.writeString(province)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BillingDetails> {
        override fun createFromParcel(parcel: Parcel): BillingDetails {
            return BillingDetails(parcel)
        }

        override fun newArray(size: Int): Array<BillingDetails?> {
            return arrayOfNulls(size)
        }
    }
}


data class CustomData(
    @SerializedName("alpha")
    val alpha: String?=null,
    @SerializedName("beta")
    val beta: String?=null,
    @SerializedName("delta")
    val delta: String?=null,
    @SerializedName("epsilon")
    val epsilon: String?=null,
    @SerializedName("gamma")
    val gamma: String?=null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(alpha)
        parcel.writeString(beta)
        parcel.writeString(delta)
        parcel.writeString(epsilon)
        parcel.writeString(gamma)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CustomData> {
        override fun createFromParcel(parcel: Parcel): CustomData {
            return CustomData(parcel)
        }

        override fun newArray(size: Int): Array<CustomData?> {
            return arrayOfNulls(size)
        }
    }
}


data class CustomerDetails(
    @SerializedName("code")
    val code: String?=null,
    @SerializedName("email")
    val email: String?=null,
    @SerializedName("m_customer_id")
    val mCustomerId: String?=null,
    @SerializedName("mobile")
    val mobile: String?=null,
    @SerializedName("name")
    val name: String?=null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(email)
        parcel.writeString(mCustomerId)
        parcel.writeString(mobile)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CustomerDetails> {
        override fun createFromParcel(parcel: Parcel): CustomerDetails {
            return CustomerDetails(parcel)
        }

        override fun newArray(size: Int): Array<CustomerDetails?> {
            return arrayOfNulls(size)
        }
    }
}


data class OrderDetails(
    @SerializedName("amount")
    val amount: String?=null,
    @SerializedName("convenience_fee")
    val convenienceFee: String?=null,
    @SerializedName("currency")
    val currency: String?=null,
    @SerializedName("description")
    val description: String?=null,
    @SerializedName("m_order_id")
    val mOrderId: String?=null,
    @SerializedName("quantity")
    val quantity: String?=null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(amount)
        parcel.writeString(convenienceFee)
        parcel.writeString(currency)
        parcel.writeString(description)
        parcel.writeString(mOrderId)
        parcel.writeString(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}


data class Parameter(
    @SerializedName("alpha")
    val alpha: String?=null,
    @SerializedName("beta")
    val beta: String?=null,
    @SerializedName("delta")
    val delta: String?=null,
    @SerializedName("epsilon")
    val epsilon: String?=null,
    @SerializedName("gamma")
    val gamma: String?=null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(alpha)
        parcel.writeString(beta)
        parcel.writeString(delta)
        parcel.writeString(epsilon)
        parcel.writeString(gamma)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Parameter> {
        override fun createFromParcel(parcel: Parcel): Parameter {
            return Parameter(parcel)
        }

        override fun newArray(size: Int): Array<Parameter?> {
            return arrayOfNulls(size)
        }
    }
}


data class ShippingDetails(
    @SerializedName("address_line1")
    val addressLine1: String?=null,
    @SerializedName("address_line2")
    val addressLine2: String?=null,
    @SerializedName("city")
    val city: String?=null,
    @SerializedName("country")
    val country: String?=null,
    @SerializedName("location_pin")
    val locationPin: String?=null,
    @SerializedName("pin")
    val pin: String?=null,
    @SerializedName("province")
    val province: String?=null,
    @SerializedName("shipping_amount")
    val shippingAmount: String?=null,
    @SerializedName("shipping_code")
    val shippingCode: String?=null,
    @SerializedName("shipping_currency")
    val shippingCurrency: String?=null,
    @SerializedName("shipping_email")
    val shippingEmail: String?=null,
    @SerializedName("shipping_mobile")
    val shippingMobile: String?=null,
    @SerializedName("shipping_name")
    val shippingName: String?=null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(addressLine1)
        parcel.writeString(addressLine2)
        parcel.writeString(city)
        parcel.writeString(country)
        parcel.writeString(locationPin)
        parcel.writeString(pin)
        parcel.writeString(province)
        parcel.writeString(shippingAmount)
        parcel.writeString(shippingCode)
        parcel.writeString(shippingCurrency)
        parcel.writeString(shippingEmail)
        parcel.writeString(shippingMobile)
        parcel.writeString(shippingName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShippingDetails> {
        override fun createFromParcel(parcel: Parcel): ShippingDetails {
            return ShippingDetails(parcel)
        }

        override fun newArray(size: Int): Array<ShippingDetails?> {
            return arrayOfNulls(size)
        }
    }
}

data class Urls(
    @SerializedName("cancel")
    val cancel: String?=null,
    @SerializedName("failure")
    val failure: String?=null,
    @SerializedName("success")
    val success: String?=null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cancel)
        parcel.writeString(failure)
        parcel.writeString(success)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Urls> {
        override fun createFromParcel(parcel: Parcel): Urls {
            return Urls(parcel)
        }

        override fun newArray(size: Int): Array<Urls?> {
            return arrayOfNulls(size)
        }
    }
}




