
# Payment

A brief description of what this project does and who it's for


# How to Add the SDK to Your Project

To integrate the `sdk.aar` file into your project, follow these steps:

## Step 1: Add the `sdk.aar` File to the `libs` Folder

    1. Navigate to your project directory:   
       app/libs
    2. If the `libs` folder does not exist inside the `app` folder, create it manually.
    3. Copy and paste the `sdk.aar` file into the `libs` folder.

## Step 2: Update the Build File

### For Groovy (`build.gradle`)
Add the following lines to the `app/build.gradle` file:

```groovy
dependencies {
    implementation files('libs/sdk.aar')

}

```


### For Koltin (`build.gradle`)
Add the following lines to the `app/build.gradle.kts` file:

```Koltin

dependencies {
    implementation(files("libs/sdk.aar"))
}

```
## Installation

Step 1: Add following library and annotation processor to your app gradle file.

```bash
implementation("com.squareup.retrofit2:retrofit:<latest_version>")
implementation("com.squareup.retrofit2:converter-gson:<latest_version>")
```


## Initial setup
### Application.kt
Add the following lines to the your `Application.kt` file:
```groovy
import android.app.Application
import com.payorc.payment.config.PayOrcConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        PayOrcConfig.getInstance().init(
                merchantKey = "*******", merchantSecret = "******", env = "live"
        )
    }
}
```


# Implementation
Step 1: Add the following lines to the your activity file:

```
import com.payorc.payment.model.dto.PaymentActionType
import com.payorc.payment.model.dto.PaymentCaptureMethod
import com.payorc.payment.model.dto.PaymentClassType
import com.payorc.payment.model.order_create.BillingDetails
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


class PaymentFormActivity : AppCompatActivity() {

    // Initialication of payment status broadcastReceiver

    private lateinit var broadcastReceiver: BroadcastReceiver



     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ......

        init()
     }

     fun init(){
          broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val status = intent?.getBooleanExtra(PayOrcConstants.PAYMENT_RESULT_STATUS, false)
                if (status == true) {
                    val data =
                        intent.parcelable<PayOrcTransaction>(PayOrcConstants.PAYMENT_RESULT_DATA)
                    Log.e("broadcastReceiver", "" + data)
                    Toast.makeText(this@PaymentFormActivity, "Payment Success Completed", Toast.LENGTH_SHORT)
                        .show()
                    finish()

                } else {
                    val error = intent?.getStringExtra(PayOrcConstants.PAYMENT_ERROR_MESSAGE)
                    Log.e("broadcastReceiver", "$error")
                    showError("Payment Failed ($error)")
                }
            }
        }

        // 
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter(PayOrcConstants.PAY_ORC_PAYMENT_RESULT))

     }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }


}

```
Payment response data should he handled in [PayOrcTransactionResponse.kt](./payorcpayment/src/main/java/com/payorc/payment/model/order_status/PayOrcTransactionResponse.kt)


Step 2: To start payment page from your activity with request ([PayOrcCreatePaymentRequest.kt](./payorcpayment/src/main/java/com/payorc/payment/model/order_create/PayOrcCreatePaymentRequest.kt)):

```
import com.payorc.payment.ui.PayOrcPaymentActivity

val intent = Intent(this, PayOrcPaymentActivity::class.java)
intent.putExtra(PayOrcConstants.KEY_CREATE_ORDER, paymentRequest)
startActivity(intent)

```

Sample Payment request:
```

import com.payorc.payment.model.order_create.PaymentRequest

val paymentRequest = PaymentRequest(
            action = PaymentActionType.AUTH.displayName, // AUTH or SALE
            billingDetails = BillingDetails(
                addressLine1 = "address1",
                addressLine2 = "address2",
                city = "Amarpur",
                country = "IN",
                pin = "482008",
                province = "Bihar"
            ),
            captureMethod = PaymentCaptureMethod.MANUAL.displayName, // MANUAL or AUTOMATIC
            classX = PaymentClassType.ECOM.displayName,
            customData = null,
            customerDetails = CustomerDetails(
                code = "971",
                email = "pawan@ payorc.com",
                mCustomerId = "123",
                mobile = "987654321",
                name = "John Doe"
            ),
            orderDetails = OrderDetails(
                amount = "100",
                convenienceFee = "0",
                currency = "AED",
                description = "Test",
                mOrderId = "1234",
                quantity = "2"
            ),
            parameters = null,
            paymentToken = null,
            shippingDetails = ShippingDetails(
                addressLine1 = "address1",
                addressLine2 = "address2",
                city = "Jabalpur",
                country = "IN",
                locationPin = "https://location/somepoint",
                pin = "482005",
                province = "Madhya Pradesh",
                shippingAmount = "10",
                shippingCode = "91",
                shippingCurrency = "AED",
                shippingEmail = "",
                shippingMobile = "9876543210",
                shippingName = " Pawan Kushwaha"
            ), urls = Urls(cancel = "", failure = "", success = "")
        )

```


