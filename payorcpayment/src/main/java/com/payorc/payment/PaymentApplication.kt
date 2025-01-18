package com.payorc.payment

import android.app.Application
import com.payorc.payment.repository.Repository
import com.payorc.payment.repository.RepositoryImpl
import com.payorc.payment.service.RetrofitInstance

class PaymentApplication : Application() {


/*
    private val retrofitInstance: RetrofitInstance by lazy {
        RetrofitInstance
    }

    // Lazy initialization of MyRepository
    val myRepository: Repository by lazy {
        RepositoryImpl(retrofitInstance.apiService)
    }

    companion object {
        private var instance: PaymentApplication? = null

        fun getInstance(): PaymentApplication {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }*/

}