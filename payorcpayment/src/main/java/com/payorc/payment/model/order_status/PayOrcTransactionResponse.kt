package com.payorc.payment.model.order_status

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PayOrcTransactionResponse(
    @SerializedName("code") val code: String? = null,
    @SerializedName("data") val transaction: PayOrcTransaction,
    @SerializedName("message") val message: String? = null,
    @SerializedName("status") val status: String? = null
) : Parcelable

@Parcelize
data class PayOrcTransaction(
    @SerializedName("amount") val amount: String? = null,
    @SerializedName("amount_caputred") val amountCaputred: String? = null,
    @SerializedName("apm_identifier") val apmIdentifier: String? = null,
    @SerializedName("apm_name") val apmName: String? = null,
    @SerializedName("channel") val channel: String? = null,
    @SerializedName("currency") val currency: String? = null,
    @SerializedName("m_customer_id") val mCustomerId: String? = null,
    @SerializedName("m_order_id") val mOrderId: String? = null,
    @SerializedName("m_payment_token") val mPaymentToken: String? = null,
    @SerializedName("p_order_id") val pOrderId: String? = null,
    @SerializedName("p_request_id") val pRequestId: Int,
    @SerializedName("payment_method") val paymentMethod: String? = null,
    @SerializedName("payment_method_data") val paymentMethodData: PaymentMethod? = null,
    @SerializedName("psp") val psp: String? = null,
    @SerializedName("psp_ref_id") val pspRefId: String? = null,
    @SerializedName("psp_txn_id") val pspTxnId: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("sub_merchant_identifier") val subMerchantIdentifier: String? = null,
    @SerializedName("transaction_date") val transactionDate: String? = null,
    @SerializedName("transaction_history") val transactionHistory: List<TransactionHistory>? = null,
    @SerializedName("transaction_id") val transactionId: String? = null
) : Parcelable

@Parcelize
data class PaymentMethod(
    @SerializedName("card_country") val cardCountry: String? = null,
    @SerializedName("card_type") val cardType: String? = null,
    @SerializedName("masked_pan") val maskedPan: String? = null,
    @SerializedName("scheme") val scheme: String? = null
) : Parcelable

@Parcelize
data class TransactionHistory(
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("order_id") val orderId: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("transaction_id") val transactionId: String? = null,
    @SerializedName("type") val type: String? = null
) : Parcelable




