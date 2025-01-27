
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
    implementation(name: 'sdk', ext: 'aar')
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