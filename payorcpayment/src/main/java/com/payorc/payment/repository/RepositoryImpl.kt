package com.payorc.payment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.payorc.payment.model.keys_secret.KeySecretRequest
import com.payorc.payment.model.keys_secret.KeySecretResponse
import com.payorc.payment.service.MyApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(private val apiService: MyApiService): Repository {

    private val _data = MutableLiveData<KeySecretResponse>()
    override val data: LiveData<KeySecretResponse> get() = _data

    override suspend fun checkKeysSecret(request: KeySecretRequest) {

        withContext(Dispatchers.IO) {
            try {
                val response = apiService.checkKeysSecret(request)
                if (response.isSuccessful) {
                    _data.postValue(response.body())
                } else {
                    // Handle API error
                    _data.postValue(null)
                }
            } catch (e: Exception) {
                // Handle network error
                _data.postValue(null)
            }
        }
    }

}