package com.payorc.payment.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.payorc.payment.databinding.ActivityPayOrcPaymentBinding
import com.payorc.payment.model.order_create.PayOrcCreatePaymentRequest
import com.payorc.payment.model.order_create.PayOrcCreatePaymentResponse
import com.payorc.payment.model.order_create.PaymentRequest
import com.payorc.payment.repository.PayOrcRepository
import com.payorc.payment.repository.PayOrcRepositoryImpl
import com.payorc.payment.service.PayOrcRetrofitInstance
import com.payorc.payment.service.PayOrcViewModelFactory
import com.payorc.payment.utils.PayOrcConstants
import com.payorc.payment.utils.parcelable
import kotlinx.coroutines.launch
import org.json.JSONObject

class PayOrcPaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayOrcPaymentBinding

    private lateinit var payOrcViewModel: PayOrcViewModel

    private val retrofitInstance: PayOrcRetrofitInstance by lazy {
        PayOrcRetrofitInstance
    }

    // Lazy initialization of MyRepository
    private val myRepository: PayOrcRepository by lazy {
        PayOrcRepositoryImpl(retrofitInstance.apiService, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOrcPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Back is pressed... Finishing the activity
                Log.e("onBackPressedDispatcher","Back pressed")
                if(binding.close.isVisible) {
                    binding.close.performClick()
                }
                else{
                    binding.webView.goBack()
                }
            }
        })
        val request = intent.parcelable<PaymentRequest>(PayOrcConstants.KEY_CREATE_ORDER)
        // Access MyRepository from Application class
        val myRepository = myRepository

        // Initialize ViewModel with MyRepository
        val viewModelFactory = PayOrcViewModelFactory(myRepository)
        payOrcViewModel = ViewModelProvider(this, viewModelFactory)[PayOrcViewModel::class.java]

        binding.close.setOnClickListener {
            val intent = Intent(PayOrcConstants.PAY_ORC_PAYMENT_RESULT)
            intent.putExtra(PayOrcConstants.PAYMENT_RESULT_STATUS, true)
            intent.putExtra(
                PayOrcConstants.PAYMENT_RESULT_DATA,
                payOrcViewModel.uiState.value.transaction
            )
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            finish() // Close the activity
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                payOrcViewModel.uiState.collect { uiState ->

                    binding.progressBar.isVisible = uiState.isLoading
                    binding.close.isVisible = uiState.orderStatusSuccess

                    uiState.errorToastMessage?.let { message ->
                        showToast(message)
                        payOrcViewModel.clearErrorMessage()
                        errorHandling(message)
                    }

                    if (uiState.checkKeySuccess) {
                        Log.e("checkKeyApi", "Valid")
                        payOrcViewModel.checkKeySuccess()
                        if (uiState.checkKeyApi) {
                            if (request != null) payOrcViewModel.createOrder(
                                request = PayOrcCreatePaymentRequest(
                                    data = request
                                )
                            )
                            else showToast("Invalid Data")
                        }
                    }
                    if (uiState.createOrderSuccess) {
                        payOrcViewModel.clearCreateOrderSuccess()
                        uiState.createOrderResponse?.let {
                            loadWebView(orderResponse = it)
                        }
                    }
                }
            }
        }
    }

    private fun errorHandling(message: String) {
        val intent = Intent(PayOrcConstants.PAY_ORC_PAYMENT_RESULT)
        intent.putExtra(PayOrcConstants.PAYMENT_RESULT_STATUS, false)
        intent.putExtra(PayOrcConstants.PAYMENT_ERROR_MESSAGE, message)
        LocalBroadcastManager.getInstance(this@PayOrcPaymentActivity).sendBroadcast(intent)
        finish() // Close the activity
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView(orderResponse: PayOrcCreatePaymentResponse) {
        // Enable JavaScript
        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true

        // Optional settings
        webSettings.domStorageEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true

        // Add JavaScript Interface for communication
        binding.webView.addJavascriptInterface(WebAppInterface(), "AndroidBridge")


        // Set a WebViewClient to handle navigation
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?, request: WebResourceRequest?
            ): Boolean {
                return false // Load URL in the WebView itself
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                // Display an error message or custom UI
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.progressBar.isVisible = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.e("WebView", "url $url")
                binding.progressBar.isVisible = false
                // Inject JavaScript after the page loads
                injectJavaScript()
            }
        }

        // Set a WebChromeClient to handle JavaScript alerts, titles, and progress
        binding.webView.webChromeClient = WebChromeClient()

        // Load a URL
        if (orderResponse.iframeLink != null) binding.webView.loadUrl(orderResponse.iframeLink)
        else showToast("Payment Page Not Loading!")
    }

    // JavaScript injection logic
    private fun injectJavaScript() {
        Log.e("WebView", "injectJavaScript")
        binding.webView.evaluateJavascript(
            """
            console.log("Injecting JavaScript...");
            var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
            var eventer = window[eventMethod];
            var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";

            // Listen to message from child window
            eventer(messageEvent, function(e) {
                console.log("Message received: ", e.data);
                AndroidBridge.onPostMessage(e.data);
            }, false);
        """.trimIndent(), null
        )
    }

    // JavaScript Interface to handle post messages
    inner class WebAppInterface {
        @JavascriptInterface
        fun onPostMessage(data: String) {
            try {
                // Parse the JSON data
                val jsonData = JSONObject(data)
                Log.d("WebView", "Received data: $jsonData")
                if (!jsonData.optString("p_order_id").isNullOrEmpty()) {
                    payOrcViewModel.checkPaymentStatus(pOrderId = jsonData.optString("p_order_id"))
                }
            } catch (e: Exception) {
                Log.e("WebView", "Error parsing JSON: ${e.message}")
            }
        }
    }

    private fun showToast(it: String) {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }
}