package com.payorc.payment

import android.app.Application
import com.payorc.payment.repository.Repository
import com.payorc.payment.repository.RepositoryImpl
import com.payorc.payment.service.RetrofitInstance

class App :Application(){

    val retrofitInstance: RetrofitInstance by lazy {
        RetrofitInstance
    }

    // Lazy initialization of MyRepository
    val myRepository: Repository by lazy {
        RepositoryImpl(retrofitInstance.apiService)
    }



}