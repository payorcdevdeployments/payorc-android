package com.payorc.payment.service

import com.payorc.payment.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PayOrcRetrofitInstance {

    val apiService: PayOrcApiService by lazy {
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PayOrcApiService::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return client
    }
}