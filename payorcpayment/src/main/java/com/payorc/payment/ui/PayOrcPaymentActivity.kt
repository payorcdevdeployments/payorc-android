package com.payorc.payment.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Gravity
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.payorc.payment.R
import com.payorc.payment.config.PayOrcConfig
import com.payorc.payment.databinding.ActivityPayOrcPaymentBinding
import com.payorc.payment.model.keys_secret.PayOrcKeySecretRequest
import com.payorc.payment.model.order_create.PayOrcCreatePaymentRequest
import com.payorc.payment.model.order_create.PayOrcCreatePaymentResponse
import com.payorc.payment.model.order_create.PaymentRequest
import com.payorc.payment.repository.PayOrcRepository
import com.payorc.payment.repository.PayOrcRepositoryImpl
import com.payorc.payment.service.PayOrcRetrofitInstance
import com.payorc.payment.utils.GIFView
import com.payorc.payment.utils.PayOrcConstants
import com.payorc.payment.utils.parcelable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

class PayOrcPaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayOrcPaymentBinding

    private val retrofitInstance: PayOrcRetrofitInstance by lazy {
        PayOrcRetrofitInstance
    }

    // Lazy initialization of MyRepository
    private val myRepository: PayOrcRepository by lazy {
        PayOrcRepositoryImpl(retrofitInstance.apiService, this)
    }

    /// to manage the gif view loader
    private var gifView: GIFView? = null
    private var countDownTimer: CountDownTimer? = null

    private fun initLoader() {
        gifView = GIFView(this, R.raw.loader).apply {
            id = View.generateViewId()  // Generate a unique ID
            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.gravity = Gravity.CENTER
            setLayoutParams(layoutParams)
        }

        // Add the view to ConstraintLayout FIRST
        binding.main.addView(gifView)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOrcPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLoader()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Back is pressed... Finishing the activity
                Log.e("onBackPressedDispatcher", "Back pressed")
                if (binding.close.isVisible) {
                    binding.close.performClick()
                } else {
                    binding.webView.goBack()
                }
            }
        })
        val request = intent.parcelable<PaymentRequest>(PayOrcConstants.KEY_CREATE_ORDER)
        // Access MyRepository from Application class
        val myRepository = myRepository

        binding.close.setOnClickListener {
            val intent = Intent(PayOrcConstants.PAY_ORC_PAYMENT_RESULT)
            intent.putExtra(PayOrcConstants.PAYMENT_RESULT_STATUS, true)
            intent.putExtra(
                PayOrcConstants.PAYMENT_RESULT_DATA, myRepository.uiState.value.transaction
            )
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            finish() // Close the activity
        }

        binding.redirectButton.setOnClickListener {
            val intent = Intent(PayOrcConstants.PAY_ORC_PAYMENT_RESULT)
            intent.putExtra(PayOrcConstants.PAYMENT_RESULT_STATUS, true)
            intent.putExtra(
                PayOrcConstants.PAYMENT_RESULT_DATA, myRepository.uiState.value.transaction
            )
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            finish() // Close the activity
        }
        lifecycleScope.launch {
            myRepository.uiState.collect { uiState ->

                gifView?.isVisible = uiState.isLoading
                binding.close.isVisible = uiState.orderStatusSuccess

                if (uiState.orderStatusSuccess) {
                    delay(2000)
                    startCountdown()
                }

                uiState.errorToastMessage?.let { message ->
                    showToast(message)
                    myRepository.clearErrorMessage()
                    errorHandling(message)
                }

                if (uiState.checkKeySuccess) {
                    Log.e("checkKeyApi", "Valid")
                    myRepository.checkKeySuccess()
                    if (uiState.checkKeyApi) {
                        if (request != null) myRepository.createOrder(
                            request = PayOrcCreatePaymentRequest(
                                data = request
                            )
                        )
                        else showToast("Invalid Data")
                    }
                }
                if (uiState.createOrderSuccess) {
                    myRepository.clearCreateOrderSuccess()
                    uiState.createOrderResponse?.let {
                        loadWebView(orderResponse = it)
                    }
                }
            }
        }
        initApi()
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }

    private fun startCountdown() {
        countDownTimer?.cancel() // Cancel any existing countdown
        binding.bottomLayout.isVisible = true
        binding.timer.text = getString(R.string.seconds_10, "5")
        countDownTimer = object : CountDownTimer(6_000, 1_000) { // 10 seconds countdown
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.timer.text = getString(R.string.seconds_10, secondsRemaining.toString())
            }

            override fun onFinish() {
                countDownTimer?.cancel()
                if (!isDestroyed) binding.close.performClick()
            }
        }.start()
    }

    private fun initApi() {
        lifecycleScope.launch {
            myRepository.checkKeysSecret(
                PayOrcKeySecretRequest(
                    merchantSecret = PayOrcConfig.getInstance().merchantSecret,
                    merchantKey = PayOrcConfig.getInstance().merchantKey,
                    env = PayOrcConfig.getInstance().env
                )
            )
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
                view: WebView?, request: WebResourceRequest?, error: WebResourceError?
            ) {
                // Display an error message or custom UI
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                gifView?.isVisible = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.e("WebView", "url $url")
                gifView?.isVisible = false
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
                    lifecycleScope.launch {
                        myRepository.checkPaymentStatus(pOrderId = jsonData.optString("p_order_id"))
                    }
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