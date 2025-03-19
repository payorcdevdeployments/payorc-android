package com.payorc.payment.config

class PayOrcConfig {

    var merchantKey: String? = null
    var merchantSecret: String? = null
    var env: String? = null
    var versionName: String = "0.0.1"

    companion object : PayOrcSingleton<PayOrcConfig>(creator = { PayOrcConfig() })

    fun init(
        merchantKey: String, merchantSecret: String, env: String
    ) {
        this.merchantKey = merchantKey
        this.merchantSecret = merchantSecret
        this.env = env
    }
}