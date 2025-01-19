package com.payorc.payment.repository

import androidx.lifecycle.LiveData
import com.payorc.payment.model.keys_secret.KeySecretRequest
import com.payorc.payment.model.keys_secret.KeySecretResponse
import com.payorc.payment.model.order_create.CreatePaymentRequest
import com.payorc.payment.ui.PaymentUiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface Repository {


    val uiState: StateFlow<PaymentUiState>

    suspend fun checkKeysSecret(request: KeySecretRequest)

    suspend fun createOrder(request: CreatePaymentRequest)

    suspend fun checkPaymentStatus(orderId:String)

    fun clearErrorMessage()
    fun checkKeySuccess()
    fun clearCreateOrderSuccesss()

}