package com.payorc.payment.repository

import com.payorc.payment.model.keys_secret.PayOrcKeySecretRequest
import com.payorc.payment.model.order_create.PayOrcCreatePaymentRequest
import com.payorc.payment.ui.PaymentUiState
import kotlinx.coroutines.flow.StateFlow

interface PayOrcRepository {

    val uiState: StateFlow<PaymentUiState>

    suspend fun checkKeysSecret(request: PayOrcKeySecretRequest)

    suspend fun createOrder(request: PayOrcCreatePaymentRequest)

    suspend fun checkPaymentStatus(orderId: String)

    fun clearErrorMessage()

    fun checkKeySuccess()

    fun clearCreateOrderSuccess()
}