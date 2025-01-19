package com.payorc.payment.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.payorc.payment.model.ErrorBody
import com.payorc.payment.model.keys_secret.KeySecretRequest
import com.payorc.payment.model.keys_secret.KeySecretResponse
import com.payorc.payment.model.order_create.CreatePaymentRequest
import com.payorc.payment.service.MyApiService
import com.payorc.payment.ui.PaymentUiState
import com.payorc.payment.utils.errorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class RepositoryImpl(private val apiService: MyApiService): Repository {


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
    override fun clearCreateOrderSuccesss() {
        _uiState.update {
            it.copy(
                createOrderSuccess = false
            )
        }
    }

    override suspend fun checkKeysSecret(request: KeySecretRequest) {

        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.checkKeysSecret(request)
                if (response.isSuccessful) {

                    if (response.code() in 200 ..299) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                checkKeySuccess = true,
                                checkKeyApi = true
                            )
                        }
                    }
                    else {
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
                        isLoading = false,
                        checkKeyApi = false,
                        errorToastMessage = "Invalid API"
                    )
                }
            }
        }
    }

    override suspend fun createOrder(request: CreatePaymentRequest) {

        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        withContext(Dispatchers.IO) {
            try {
                val response = apiService.createOrder(request= request)
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
                        isLoading = false,
                        errorToastMessage = "Failed to create order"
                    )
                }
            }
        }
    }

    override suspend fun checkPaymentStatus(orderId: String) {

        _uiState.update {
            it.copy(
                isLoading = true
            )
        }


        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPaymentDetails(orderId)
                if (response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orderStatusSuccess = true,
                            orderStatus = response.body()?.orderStatus
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
                        isLoading = false,
                        errorToastMessage = "Failed to create order"
                    )
                }
            }
        }
    }
}