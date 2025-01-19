package com.payorc.payment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payorc.payment.config.PayOrcConfig
import com.payorc.payment.model.keys_secret.KeySecretRequest
import com.payorc.payment.model.keys_secret.KeySecretResponse
import com.payorc.payment.model.order_create.CreatePaymentRequest
import com.payorc.payment.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel(private val repository: Repository):ViewModel() {

    val uiState:StateFlow<PaymentUiState> = repository.uiState

    fun checkKeysSecret() {

        viewModelScope.launch {
            repository.checkKeysSecret(KeySecretRequest(
                merchantSecret = PayOrcConfig.getInstance().merchantSecret,
                merchantKey = PayOrcConfig.getInstance().merchantKey,
                env = PayOrcConfig.getInstance().env
            ))
        }
    }

    fun clearErrorMessage() {
        repository.clearErrorMessage()
    }

    fun createOrder(request: CreatePaymentRequest) {
        viewModelScope.launch {
            repository.createOrder(request)
        }
    }

    fun checkKeySuccess() {
        repository.checkKeySuccess()
    }

    fun clearCreateOrderSuccesss() {
        repository.clearCreateOrderSuccesss()
    }

    fun checkPaymentStatus(pOrderId: String) {
        viewModelScope.launch {
            repository.checkPaymentStatus(pOrderId)
        }
    }
}