package com.payorc.payment.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.payorc.payment.PaymentApplication
import com.payorc.payment.databinding.ActivityPayOrcPaymentBinding
import com.payorc.payment.repository.Repository
import com.payorc.payment.repository.RepositoryImpl
import com.payorc.payment.service.MyViewModelFactory
import com.payorc.payment.service.RetrofitInstance

class PayOrcPaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayOrcPaymentBinding

    private lateinit var myViewModel: MyViewModel

    private val retrofitInstance: RetrofitInstance by lazy {
        RetrofitInstance
    }

    // Lazy initialization of MyRepository
    private val myRepository: Repository by lazy {
        RepositoryImpl(retrofitInstance.apiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOrcPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Access MyRepository from Application class
        val myRepository = myRepository

        // Initialize ViewModel with MyRepository
        val viewModelFactory = MyViewModelFactory(myRepository)
        myViewModel = ViewModelProvider(this, viewModelFactory)[MyViewModel::class.java]
        myViewModel.checkKeysSecret()
        myViewModel.data.observe(this) { data ->
            // Update UI with API data
            Log.e(
                "Reposnse",""+data
            )
        }

    }

}