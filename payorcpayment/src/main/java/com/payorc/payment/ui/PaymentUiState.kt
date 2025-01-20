package com.payorc.payment.ui

import com.payorc.payment.model.order_create.PayOrcCreatePaymentResponse
import com.payorc.payment.model.order_status.PayOrcTransaction

data class PaymentUiState(
    var isLoading: Boolean = false,
    val errorToastMessage: String? = null,
    val checkKeySuccess: Boolean = false,
    val checkKeyApi: Boolean = false,
    val createOrderSuccess: Boolean = false,
    val createOrderResponse: PayOrcCreatePaymentResponse? = null,
    val orderStatusSuccess: Boolean = false,
    val transaction: PayOrcTransaction? = null
)