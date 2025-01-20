package com.sample.payorc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.payorc.payment.model.order_create.PaymentRequest
import com.payorc.payment.model.order_status.PayOrcTransaction
import com.payorc.payment.ui.PayOrcPaymentActivity
import com.payorc.payment.utils.PayOrcConstants
import com.payorc.payment.utils.jsonToGSON
import com.payorc.payment.utils.parcelable
import com.sample.payorc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val status = intent?.getBooleanExtra(PayOrcConstants.PAYMENT_RESULT_STATUS, false)
                if (status == true) {
                    val data =
                        intent.parcelable<PayOrcTransaction>(PayOrcConstants.PAYMENT_RESULT_DATA)
                    Log.e("broadcastReceiver", "" + data)
                } else {
                    val error = intent?.getStringExtra(PayOrcConstants.PAYMENT_ERROR_MESSAGE)
                    Log.e("broadcastReceiver", "$error")
                }
            }
        }

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter(PayOrcConstants.PAYMENT_RESULT_DATA))

        val data = request.jsonToGSON<PaymentRequest>()

        Log.e("jsonToGSON", "" + data)
        binding.checkPayment.setOnClickListener {
            val intent = Intent(this, PayOrcPaymentActivity::class.java)
            intent.putExtra(PayOrcConstants.KEY_CREATE_ORDER, data)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(broadcastReceiver)
    }

    private val request = """
        
    {
      "class": "ECOM",
      "action": "SALE",
      "capture_method": "MANUAL",
      "payment_token": "",
      "order_details": {
        "m_order_id": "1234",
        "amount": "100",
        "convenience_fee": "0",
        "quantity": "2",
        "currency": "AED",
        "description": ""
      },
      "customer_details": {
        "m_customer_id": "123",
        "name": "John Doe",
        "email": "pawan@payorc.com",
        "mobile": "987654321",
        "code": "971"
      },
      "billing_details": {
        "address_line1": "address 1",
        "address_line2": "address 2",
        "city": "Amarpur",
        "province": "Bihar",
        "country": "IN",
        "pin": "482008"
      },
      "shipping_details": {
        "shipping_name": "Pawan Kushwaha",
        "shipping_email": "",
        "shipping_code": "91",
        "shipping_mobile": "9876543210",
        "address_line1": "address 1",
        "address_line2": "address 2",
        "city": "Jabalpur",
        "province": "Madhya Pradesh",
        "country": "IN",
        "pin": "482005",
        "location_pin": "https://location/somepoint",
        "shipping_currency": "AED",
        "shipping_amount": "10"
      },
      "urls": {
        "success": "",
        "cancel": "",
        "failure": ""
      },
      "parameters": [
        {
          "alpha": ""
        },
        {
          "beta": ""
        },
        {
          "gamma": ""
        },
        {
          "delta": ""
        },
        {
          "epsilon": ""
        }
      ],
      "custom_data": [
        {
          "alpha": ""
        },
        {
          "beta": ""
        },
        {
          "gamma": ""
        },
        {
          "delta": ""
        },
        {
          "epsilon": ""
        }
      ]
    }
    """.trimIndent()
}