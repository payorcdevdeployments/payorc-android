package com.payorc.payment.repository

import android.content.Context
import com.payorc.payment.model.keys_secret.PayOrcKeySecretRequest
import com.payorc.payment.model.order_create.PayOrcCreatePaymentRequest
import com.payorc.payment.service.PayOrcApiService
import com.payorc.payment.ui.PaymentUiState
import com.payorc.payment.utils.errorMessage
import com.payorc.payment.utils.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class PayOrcRepositoryImpl(private val apiService: PayOrcApiService, private val context: Context) :
    PayOrcRepository {

    private val _uiState = MutableStateFlow(PaymentUiState())

    override val uiState: StateFlow<PaymentUiState>
        get() = _uiState.asStateFlow()

    override fun clearErrorMessage() {
        _uiState.update {
            it.copy(
                errorToastMessage = null
            )
        }
    }

    override fun checkKeySuccess() {
        _uiState.update {
            it.copy(
                checkKeySuccess = false
            )
        }
    }

    override fun clearCreateOrderSuccess() {
        _uiState.update {
            it.copy(
                createOrderSuccess = false
            )
        }
    }

    override suspend fun checkKeysSecret(request: PayOrcKeySecretRequest) {
        if (!context.isOnline()) {
            _uiState.update {
                it.copy(
                    errorToastMessage = "No network connection!"
                )
            }
            return
        }
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.checkKeysSecret(request)
                if (response.isSuccessful) {
                    if (response.code() in 200..299) {
                        _uiState.update {
                            it.copy(
                                isLoading = false, checkKeySuccess = true, checkKeyApi = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                checkKeyApi = false,
                                errorToastMessage = response.body()?.message
                            )
                        }
                    }
                } else {
                    // Handle API error
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            checkKeyApi = false,
                            errorToastMessage = response.errorBody()?.errorMessage()
                        )
                    }
                }
            } catch (e: Exception) {
                // Handle network error
                _uiState.update {
                    it.copy(
                        isLoading = false, checkKeyApi = false, errorToastMessage = "Invalid API"
                    )
                }
            }
        }
    }

    override suspend fun createOrder(request: PayOrcCreatePaymentRequest) {
        if (!context.isOnline()) {
            _uiState.update {
                it.copy(
                    errorToastMessage = "No network connection!"
                )
            }
            return
        }
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        withContext(Dispatchers.IO) {
            try {
                val response = apiService.createOrder(request = request)
                if (response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            createOrderSuccess = true,
                            createOrderResponse = response.body()
                        )
                    }
                } else {
                    // Handle API error
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorToastMessage = response.errorBody()?.errorMessage()
                        )
                    }
                }
            } catch (e: Exception) {
                // Handle network error
                _uiState.update {
                    it.copy(
                        isLoading = false, errorToastMessage = "Failed to create order"
                    )
                }
            }
        }
    }

    override suspend fun checkPaymentStatus(pOrderId: String) {
        if (!context.isOnline()) {
            _uiState.update {
                it.copy(
                    errorToastMessage = "No network connection!"
                )
            }
            return
        }
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPaymentDetails(pOrderId)
                if (response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orderStatusSuccess = true,
                            transaction = response.body()?.transaction
                        )
                    }
                } else {
                    // Handle API error
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorToastMessage = response.errorBody()?.errorMessage()
                        )
                    }
                }
            } catch (e: Exception) {
                // Handle network error
                _uiState.update {
                    it.copy(
                        isLoading = false, errorToastMessage = "Failed to create order"
                    )
                }
            }
        }
    }
}