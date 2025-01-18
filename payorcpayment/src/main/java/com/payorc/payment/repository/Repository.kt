package com.payorc.payment.repository

import androidx.lifecycle.LiveData
import com.payorc.payment.model.keys_secret.KeySecretRequest
import com.payorc.payment.model.keys_secret.KeySecretResponse

interface Repository {

    val data: LiveData<KeySecretResponse>

    suspend fun checkKeysSecret(request: KeySecretRequest)

}