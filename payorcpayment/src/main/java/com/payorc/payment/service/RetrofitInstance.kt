package com.payorc.payment.service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://nodeserver.payorc.com/api/"
    private const val API_VERSION ="v1/"

    val apiService: MyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL+API_VERSION)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApiService::class.java)
    }
}