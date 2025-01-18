package com.sample.payorc

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.payorc.payment.databinding.ActivityPayOrcPaymentBinding
import com.payorc.payment.ui.PayOrcPaymentActivity
import com.sample.payorc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkPayment.setOnClickListener {
            val intent = Intent(this, PayOrcPaymentActivity::class.java)
            startActivity(intent)
        }
    }
}