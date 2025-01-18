package com.payorc.payment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payorc.payment.config.PayOrcConfig
import com.payorc.payment.model.keys_secret.KeySecretRequest
import com.payorc.payment.model.keys_secret.KeySecretResponse
import com.payorc.payment.repository.Repository
import kotlinx.coroutines.launch

class MyViewModel(private val repository: Repository):ViewModel() {


    val data: LiveData<KeySecretResponse> get() = repository.data

    fun checkKeysSecret() {

        viewModelScope.launch {
            repository.checkKeysSecret(KeySecretRequest(
                merchantSecret = PayOrcConfig.getInstance().merchantSecret,
                merchantKey = PayOrcConfig.getInstance().merchantKey,
                env = PayOrcConfig.getInstance().env
            ))
        }
    }
}