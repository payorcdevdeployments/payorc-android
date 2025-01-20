package com.payorc.payment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payorc.payment.config.PayOrcConfig
import com.payorc.payment.model.keys_secret.PayOrcKeySecretRequest
import com.payorc.payment.model.order_create.PayOrcCreatePaymentRequest
import com.payorc.payment.repository.PayOrcRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PayOrcViewModel(private val repository: PayOrcRepository) : ViewModel() {

    val uiState: StateFlow<PaymentUiState> = repository.uiState

    init {
        checkKeysSecret()
    }

    private fun checkKeysSecret() {

        viewModelScope.launch {
            repository.checkKeysSecret(
                PayOrcKeySecretRequest(
                    merchantSecret = PayOrcConfig.getInstance().merchantSecret,
                    merchantKey = PayOrcConfig.getInstance().merchantKey,
                    env = PayOrcConfig.getInstance().env
                )
            )
        }
    }

    fun clearErrorMessage() {
        repository.clearErrorMessage()
    }

    fun createOrder(request: PayOrcCreatePaymentRequest) {
        viewModelScope.launch {
            repository.createOrder(request)
        }
    }

    fun checkKeySuccess() {
        repository.checkKeySuccess()
    }

    fun clearCreateOrderSuccess() {
        repository.clearCreateOrderSuccess()
    }

    fun checkPaymentStatus(pOrderId: String) {
        viewModelScope.launch {
            repository.checkPaymentStatus(pOrderId)
        }
    }
}