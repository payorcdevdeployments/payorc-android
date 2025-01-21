package com.payorc.payment.model.dto


enum class PaymentActionType(val displayName: String) {
    SELECT("SELECT ACTION"),
    AUTH("AUTH"), SALE("SALE");
}