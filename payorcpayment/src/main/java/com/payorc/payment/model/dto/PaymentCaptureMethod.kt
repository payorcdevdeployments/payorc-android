package com.payorc.payment.model.dto


enum class PaymentCaptureMethod(val displayName: String) {
    SELECT("SELECT CAPTURE METHOD"),
    MANUAL("MANUAL"), AUTOMATIC("AUTOMATIC");
}