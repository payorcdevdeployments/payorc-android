package com.sample.payorc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.payorc.payment.model.dto.PaymentActionType
import com.payorc.payment.model.dto.PaymentCaptureMethod
import com.payorc.payment.model.dto.PaymentClassType
import com.payorc.payment.model.order_create.BillingDetails
import com.payorc.payment.model.order_create.CustomData
import com.payorc.payment.model.order_create.CustomerDetails
import com.payorc.payment.model.order_create.OrderDetails
import com.payorc.payment.model.order_create.Parameter
import com.payorc.payment.model.order_create.PaymentRequest
import com.payorc.payment.model.order_create.ShippingDetails
import com.payorc.payment.model.order_create.Urls
import com.payorc.payment.model.order_status.PayOrcTransaction
import com.payorc.payment.ui.PayOrcPaymentActivity
import com.payorc.payment.utils.PayOrcConstants
import com.payorc.payment.utils.parcelable
import com.sample.payorc.databinding.ActivityPaymentFormBinding

class PaymentFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentFormBinding
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolBar()
        initSpinner()

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val status = intent?.getBooleanExtra(PayOrcConstants.PAYMENT_RESULT_STATUS, false)
                if (status == true) {
                    val data =
                        intent.parcelable<PayOrcTransaction>(PayOrcConstants.PAYMENT_RESULT_DATA)
                    Log.e("broadcastReceiver", "" + data)
                    finish()
                } else {
                    val error = intent?.getStringExtra(PayOrcConstants.PAYMENT_ERROR_MESSAGE)
                    Log.e("broadcastReceiver", "$error")
                    showError("Payment Failed ($error)")
                }
            }
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(
            broadcastReceiver, IntentFilter(PayOrcConstants.PAY_ORC_PAYMENT_RESULT)
        )

        binding.btnSubmit.setOnClickListener {
            if (binding.spinnerClassName.selectedItemPosition == 0) {
                showError("Please select a class name")
                return@setOnClickListener
            }
            if (binding.spinnerAction.selectedItemPosition == 0) {
                showError("Please select an action")
                return@setOnClickListener
            }
            if (binding.spinnerCaptureMethod.selectedItemPosition == 0) {
                showError("Please select a capture method")
                return@setOnClickListener
            }

            // Proceed with the submission
            submitForm()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

    private fun submitForm() {
        val paymentRequest =
            PaymentRequest(classX = PaymentClassType.entries[binding.spinnerClassName.selectedItemPosition].displayName,
                action = PaymentActionType.entries[binding.spinnerAction.selectedItemPosition].displayName,
                captureMethod = PaymentCaptureMethod.entries[binding.spinnerCaptureMethod.selectedItemPosition].displayName,

                // order details
                orderDetails = OrderDetails(
                    amount = binding.etAmount.text.toString(),
                    currency = binding.etCurrency.text.toString(),
                    description = binding.etDescription.text.toString(),
                    quantity = binding.etQuantity.text.toString(),
                    convenienceFee = binding.etConvenienceFee.text.toString(),
                    mOrderId = binding.etOrderId.text.toString()
                ),
                customerDetails = CustomerDetails(
                    name = binding.etCustomerName.text.toString(),
                    email = binding.etCustomerEmail.text.toString(),
                    mobile = binding.etCustomerMobile.text.toString(),
                    code = binding.etCustomerCode.text.toString(),
                    mCustomerId = binding.etCustomerId.text.toString()
                ),
                billingDetails = BillingDetails(
                    addressLine1 = binding.etBillingAddressLine1.text.toString(),
                    addressLine2 = binding.etBillingAddressLine2.text.toString(),
                    city = binding.etBillingCity.text.toString(),
                    country = binding.etBillingCountry.text.toString(),
                    pin = binding.etBillingPin.text.toString(),
                    province = binding.etBillingProvince.text.toString()
                ),
                shippingDetails = ShippingDetails(
                    shippingName = binding.etShippingName.text.toString(),
                    shippingEmail = binding.etShippingEmail.text.toString(),
                    shippingMobile = binding.etShippingMobile.text.toString(),
                    shippingAmount = binding.etShippingAmount.text.toString(),
                    shippingCurrency = binding.etShippingCurrency.text.toString(),
                    shippingCode = binding.etShippingCode.text.toString(),
                    addressLine1 = binding.etShippingAddressLine1.text.toString(),
                    addressLine2 = binding.etShippingAddressLine2.text.toString(),
                    city = binding.etShippingCity.text.toString(),
                    country = binding.etShippingCountry.text.toString(),
                    pin = binding.etShippingPin.text.toString(),
                    locationPin = binding.etLocationPin.text.toString(),
                    province = binding.etShippingProvince.text.toString()
                ),
                urls = Urls(
                    success = binding.etSuccessUrl.text.toString(),
                    failure = binding.etFailureUrl.text.toString(),
                    cancel = binding.etCancelUrl.text.toString()
                ),
                parameters = mutableListOf<Parameter>().apply {
                    add(Parameter(alpha = binding.etParameterAlpha.text.toString()))
                    add(Parameter(beta = binding.etParameterBeta.text.toString()))
                    add(Parameter(gamma = binding.etParameterGamma.text.toString()))
                    add(Parameter(delta = binding.etParameterDelta.text.toString()))
                    add(Parameter(epsilon = binding.etParameterEpsilon.text.toString()))
                },
                customData = mutableListOf<CustomData>().apply {
                    add(CustomData(alpha = binding.etCustomDataAlpha.text.toString()))
                    add(CustomData(beta = binding.etCustomDataBeta.text.toString()))
                    add(CustomData(gamma = binding.etCustomDataGamma.text.toString()))
                    add(CustomData(delta = binding.etCustomDataEpsilon.text.toString()))
                    add(CustomData(epsilon = binding.etCustomDataEpsilon.text.toString()))
                })
        val intent = Intent(this, PayOrcPaymentActivity::class.java)
        intent.putExtra(PayOrcConstants.KEY_CREATE_ORDER, paymentRequest)
        startActivity(intent)
    }

    private fun showError(errorMessage: String) {
        // Display the error message to the user
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun initToolBar() {
        setSupportActionBar(binding.toolbar)
        // Enable the back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24) // Use your back arrow drawable
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle back arrow press
                finish()
                true
            }

            R.id.action_prefill -> {
                // Handle "Prefill" action
                // Add your prefill logic here
                setPrefillData()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setPrefillData() {
        binding.spinnerAction.setSelection(1)
        binding.spinnerClassName.setSelection(1)
        binding.spinnerCaptureMethod.setSelection(1)
        // order details
        binding.etOrderId.setText("1234")
        binding.etAmount.setText("100")
        binding.etCurrency.setText("AED")
        binding.etDescription.setText("Sample request description")
        binding.etQuantity.setText("2")
        binding.etConvenienceFee.setText("5")
        // customer details
        binding.etCustomerId.setText("123")
        binding.etCustomerName.setText("John Doe")
        binding.etCustomerEmail.setText("johndoe@example.com")
        binding.etCustomerMobile.setText("987654321")
        binding.etCustomerCode.setText("971")
        // billing details
        binding.etBillingAddressLine1.setText("address 1")
        binding.etBillingAddressLine2.setText("address 2")
        binding.etBillingCity.setText("Amarpur")
        binding.etBillingProvince.setText("Bihar")
        binding.etBillingCountry.setText("IN")
        binding.etBillingPin.setText("482008")
        // shipping details
        binding.etShippingName.setText("Pawan Kushwaha")
        binding.etShippingEmail.setText("")
        binding.etShippingCode.setText("91")
        binding.etShippingMobile.setText("9876543210")
        binding.etShippingAddressLine1.setText("address 1")
        binding.etShippingAddressLine2.setText("address 2")
        binding.etShippingCity.setText("Jabalpur")
        binding.etShippingProvince.setText("Madhya Pradesh")
        binding.etShippingCountry.setText("IN")
        binding.etShippingPin.setText("482005")
        binding.etLocationPin.setText("https://location/somepoint")
        binding.etShippingCurrency.setText("AED")
        binding.etShippingAmount.setText("10")
        // urls
        binding.etSuccessUrl.setText("")
        binding.etFailureUrl.setText("")
        binding.etCancelUrl.setText("")
        // parameters
        binding.etParameterAlpha.setText("alpha")
        binding.etParameterBeta.setText("beta")
        binding.etParameterGamma.setText("gamma")
        binding.etParameterDelta.setText("delta")
        binding.etParameterEpsilon.setText("epsilon")

        // custom data
        binding.etCustomDataAlpha.setText("alpha")
        binding.etCustomDataBeta.setText("beta")
        binding.etCustomDataGamma.setText("gamma")
        binding.etCustomDataDelta.setText("delta")
        binding.etCustomDataEpsilon.setText("epsilon")
    }

    private fun initSpinner() {


        val enumPaymentClassTypeValues = PaymentClassType.entries.map { it.displayName }
        val enumPaymentClassTypeAdapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout for each item
            enumPaymentClassTypeValues // Data to display
        )
        enumPaymentClassTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerClassName.apply {
            adapter = enumPaymentClassTypeAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View?, position: Int, id: Long
                ) {
                    // Get the selected enum value
                    val selectedEnum = PaymentClassType.entries[position]

                    setSpinnerValue(selectedEnum)

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Handle the case where no item is selected, if necessary
                }
            }
        }

        val enumActionValues = PaymentActionType.entries.map { it.displayName }
        val enumActionAdapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout for each item
            enumActionValues // Data to display
        )
        enumActionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAction.apply {
            adapter = enumActionAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View?, position: Int, id: Long
                ) {
                    // Get the selected enum value
                    val selectedEnum = PaymentActionType.entries[position]

                    setSpinnerValue(selectedEnum)

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Handle the case where no item is selected, if necessary
                }
            }
        }

        val enumCaptureMethodValues = PaymentCaptureMethod.entries.map { it.displayName }
        val enumCaptureMethodAdapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout for each item
            enumCaptureMethodValues // Data to display
        )
        enumCaptureMethodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCaptureMethod.apply {
            adapter = enumCaptureMethodAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View?, position: Int, id: Long
                ) {
                    // Get the selected enum value
                    val selectedEnum = PaymentCaptureMethod.entries[position]
                    setSpinnerValue(selectedEnum)

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Handle the case where no item is selected, if necessary
                }
            }
        }

    }

    private fun setSpinnerValue(selectedEnum: Any) {

        when (selectedEnum) {
            is PaymentClassType -> {
                binding.spinnerClassName.setSelection(selectedEnum.ordinal)
            }

            is PaymentActionType -> {
                binding.spinnerAction.setSelection(selectedEnum.ordinal)
            }

            is PaymentCaptureMethod -> {
                binding.spinnerCaptureMethod.setSelection(selectedEnum.ordinal)
            }
        }
    }
}