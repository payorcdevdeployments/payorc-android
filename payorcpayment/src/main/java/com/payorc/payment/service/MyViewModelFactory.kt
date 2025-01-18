package com.payorc.payment.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.payorc.payment.repository.Repository
import com.payorc.payment.ui.MyViewModel

class MyViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}