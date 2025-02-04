plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("maven-publish")        // << --- ADD This
}

android {
    namespace = "com.payorc.payment"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            consumerProguardFiles("proguard-rules.pro")
            consumerProguardFiles("consumer-rules.pro")      // << --- ADD This
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    flavorDimensions += listOf("dimensions")
    productFlavors {
        create("dev") {
            dimension = "dimensions"
            buildConfigField("String", "BASE_URL", "\"https://nodeserver.payorc.com\"")
            buildConfigField("String", "ENVIRONMENT", "\"test\"")
        }
        create("prod") {
            dimension = "dimensions"
            buildConfigField("String", "BASE_URL", "\"https://nodeserver.payorc.com\"")
            buildConfigField("String", "ENVIRONMENT", "\"live\"")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    // Retrofit2
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
}

// 5. Publishing:
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.esaiamuthan"
            artifactId = "pay-orc-android"
            version = "1.0.6"
            pom {
                description.set("added timer")
            }
        }
    }
    repositories {
        mavenLocal() // << --- ADD This
    }
}

