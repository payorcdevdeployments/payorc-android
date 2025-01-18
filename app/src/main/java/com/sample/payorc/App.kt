package com.sample.payorc

import android.app.Application
import com.payorc.payment.config.PayOrcConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        PayOrcConfig.getInstance().init(
            merchantKey = "test-JR11KGG26DM", merchantSecret = "sec-DC111UM26HQ", env = "test"
        )
    }
}