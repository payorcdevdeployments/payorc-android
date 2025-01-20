package com.payorc.payment.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.payorc.payment.repository.PayOrcRepository
import com.payorc.payment.ui.PayOrcViewModel

class PayOrcViewModelFactory(private val repository: PayOrcRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PayOrcViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return PayOrcViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}