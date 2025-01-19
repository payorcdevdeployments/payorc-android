package com.payorc.payment.ui

import com.payorc.payment.model.order_create.CreatePaymentResponse
import com.payorc.payment.model.order_status.OrderStatus

data class PaymentUiState (
    var isLoading:Boolean = false,
    val errorToastMessage: String? = null,
    val checkKeySuccess:Boolean = false,
    val checkKeyApi:Boolean = false,
    val createOrderSuccess: Boolean= false,
    val createOrderResponse: CreatePaymentResponse?= null,
    val orderStatusSuccess: Boolean= false,
    val orderStatus: OrderStatus?= null
)