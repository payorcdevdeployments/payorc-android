package com.payorc.payment.service

import com.payorc.payment.BuildConfig
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PayOrcRetrofitInstance {

    val apiService: PayOrcApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PayOrcApiService::class.java)
    }
}