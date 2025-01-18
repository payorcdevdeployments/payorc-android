package com.payorc.payment.config

class PayOrcConfig {

    var merchantKey:String? = null
    var merchantSecret:String? = null
    var env:String? = null

    companion object : SingletonHolderAuth<PayOrcConfig>(creator = { PayOrcConfig() })

    fun init(
        merchantKey:String,
        merchantSecret:String,
        env:String
    ){
        this.merchantKey = merchantKey
        this.merchantSecret = merchantSecret
        this.env = env
    }

}