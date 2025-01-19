package com.payorc.payment.model.order_status

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class OrderStatusResponse(
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("data")
    val orderStatus: OrderStatus,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)


data class OrderStatus(
    @SerializedName("amount")
    val amount: String? = null,
    @SerializedName("amount_caputred")
    val amountCaputred: String? = null,
    @SerializedName("apm_identifier")
    val apmIdentifier: String? = null,
    @SerializedName("apm_name")
    val apmName: String? = null,
    @SerializedName("channel")
    val channel: String? = null,
    @SerializedName("currency")
    val currency: String? = null,
    @SerializedName("m_customer_id")
    val mCustomerId: String? = null,
    @SerializedName("m_order_id")
    val mOrderId: String? = null,
    @SerializedName("m_payment_token")
    val mPaymentToken: String? = null,
    @SerializedName("p_order_id")
    val pOrderId: String? = null,
    @SerializedName("p_request_id")
    val pRequestId: Int,
    @SerializedName("payment_method")
    val paymentMethod: String? = null,
    @SerializedName("payment_method_data")
    val paymentMethodData: PaymentMethodData?=null,
    @SerializedName("psp")
    val psp: String? = null,
    @SerializedName("psp_ref_id")
    val pspRefId: String? = null,
    @SerializedName("psp_txn_id")
    val pspTxnId: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("sub_merchant_identifier")
    val subMerchantIdentifier: String? = null,
    @SerializedName("transaction_date")
    val transactionDate: String? = null,
    @SerializedName("transaction_history")
    val transactionHistory: List<TransactionData>?= null,
    @SerializedName("transaction_id")
    val transactionId: String? = null
):Parcelable {
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
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(PaymentMethodData::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(TransactionData),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(amount)
        parcel.writeString(amountCaputred)
        parcel.writeString(apmIdentifier)
        parcel.writeString(apmName)
        parcel.writeString(channel)
        parcel.writeString(currency)
        parcel.writeString(mCustomerId)
        parcel.writeString(mOrderId)
        parcel.writeString(mPaymentToken)
        parcel.writeString(pOrderId)
        parcel.writeInt(pRequestId)
        parcel.writeString(paymentMethod)
        parcel.writeParcelable(paymentMethodData, flags)
        parcel.writeString(psp)
        parcel.writeString(pspRefId)
        parcel.writeString(pspTxnId)
        parcel.writeString(status)
        parcel.writeString(subMerchantIdentifier)
        parcel.writeString(transactionDate)
        parcel.writeTypedList(transactionHistory)
        parcel.writeString(transactionId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderStatus> {
        override fun createFromParcel(parcel: Parcel): OrderStatus {
            return OrderStatus(parcel)
        }

        override fun newArray(size: Int): Array<OrderStatus?> {
            return arrayOfNulls(size)
        }
    }
}


data class PaymentMethodData(
    @SerializedName("card_country")
    val cardCountry: String? = null,
    @SerializedName("card_type")
    val cardType: String? = null,
    @SerializedName("masked_pan")
    val maskedPan: String? = null,
    @SerializedName("scheme")
    val scheme: String? = null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cardCountry)
        parcel.writeString(cardType)
        parcel.writeString(maskedPan)
        parcel.writeString(scheme)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PaymentMethodData> {
        override fun createFromParcel(parcel: Parcel): PaymentMethodData {
            return PaymentMethodData(parcel)
        }

        override fun newArray(size: Int): Array<PaymentMethodData?> {
            return arrayOfNulls(size)
        }
    }
}


data class TransactionData(
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("order_id")
    val orderId: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("transaction_id")
    val transactionId: String? = null,
    @SerializedName("type")
    val type: String? = null
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
        parcel.writeString(createdAt)
        parcel.writeString(orderId)
        parcel.writeString(status)
        parcel.writeString(transactionId)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionData> {
        override fun createFromParcel(parcel: Parcel): TransactionData {
            return TransactionData(parcel)
        }

        override fun newArray(size: Int): Array<TransactionData?> {
            return arrayOfNulls(size)
        }
    }
}




