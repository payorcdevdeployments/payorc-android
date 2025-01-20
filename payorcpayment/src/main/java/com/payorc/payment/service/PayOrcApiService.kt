package com.payorc.payment.service

import com.payorc.payment.config.PayOrcConfig
import com.payorc.payment.model.keys_secret.PayOrcKeySecretRequest
import com.payorc.payment.model.keys_secret.PayOrcKeySecretResponse
import com.payorc.payment.model.order_create.PayOrcCreatePaymentRequest
import com.payorc.payment.model.order_create.PayOrcCreatePaymentResponse
import com.payorc.payment.model.order_status.PayOrcTransactionResponse
import com.payorc.payment.utils.PayOrcApiContent
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PayOrcApiService {

    @POST(PayOrcApiContent.KEYS_SECRET)
    suspend fun checkKeysSecret(@Body request: PayOrcKeySecretRequest): Response<PayOrcKeySecretResponse>

    @POST(PayOrcApiContent.CREATE_ORDER)
    suspend fun createOrder(
        @Header("merchant-key") merchantKey: String = PayOrcConfig.getInstance().merchantKey ?: "",
        @Header("merchant-secret") merchantSecret: String = PayOrcConfig.getInstance().merchantSecret
            ?: "",
        @Body request: PayOrcCreatePaymentRequest
    ): Response<PayOrcCreatePaymentResponse>

    @GET(PayOrcApiContent.TRANSACTION_DETAILS)
    suspend fun getPaymentDetails(
        @Query("p_order_id") orderId: String,
        @Header("merchant-key") merchantKey: String = PayOrcConfig.getInstance().merchantKey ?: "",
        @Header("merchant-secret") merchantSecret: String = PayOrcConfig.getInstance().merchantSecret
            ?: "",
    ): Response<PayOrcTransactionResponse>
}