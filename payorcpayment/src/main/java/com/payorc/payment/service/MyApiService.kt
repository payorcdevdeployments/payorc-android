package com.payorc.payment.service

import com.payorc.payment.model.keys_secret.KeySecretRequest
import com.payorc.payment.model.keys_secret.KeySecretResponse
import com.payorc.payment.utils.ApiContent
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MyApiService {

    @POST(ApiContent.KEYS_SECRET)
    suspend fun checkKeysSecret(@Body request: KeySecretRequest): Response<KeySecretResponse>


}