package com.payorc.payment.service

import com.payorc.payment.config.PayOrcConfig
import com.payorc.payment.model.keys_secret.KeySecretRequest
import com.payorc.payment.model.keys_secret.KeySecretResponse
import com.payorc.payment.model.order_create.CreatePaymentRequest
import com.payorc.payment.model.order_create.CreatePaymentResponse
import com.payorc.payment.model.order_status.OrderStatusResponse
import com.payorc.payment.utils.ApiContent
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface MyApiService {

    @POST(ApiContent.KEYS_SECRET)
    suspend fun checkKeysSecret(@Body request: KeySecretRequest): Response<KeySecretResponse>


    @POST(ApiContent.CREATE_ORDER)
    suspend fun createOrder(
        @Header("merchant-key") merchantKey: String = PayOrcConfig.getInstance().merchantKey ?: "",
        @Header("merchant-secret") merchantSecret: String = PayOrcConfig.getInstance().merchantSecret
            ?: "",
        @Body request: CreatePaymentRequest
    ): Response<CreatePaymentResponse>


    @GET(ApiContent.TRANSACTION_DETAILS)
    suspend fun getPaymentDetails(
        @Query("p_order_id") orderId: String,
        @Header("merchant-key") merchantKey: String = PayOrcConfig.getInstance().merchantKey ?: "",
        @Header("merchant-secret") merchantSecret: String = PayOrcConfig.getInstance().merchantSecret
            ?: "",
    ): Response<OrderStatusResponse>


}