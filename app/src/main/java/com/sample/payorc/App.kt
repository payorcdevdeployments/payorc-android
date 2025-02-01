package com.sample.payorc

import android.app.Application
import android.content.Context
import com.payorc.payment.config.PayOrcConfig
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val config = loadConfig(this)

        PayOrcConfig.getInstance().init(
            merchantKey = config.getString("MERCHANT_KEY"),
            merchantSecret = config.getString("MERCHANT_SECRET"),
            env = config.getString("ENV")
        )
    }

    private fun loadConfig(context: Context): JSONObject {
        return try {
            val inputStream = context.assets.open("env.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonText = reader.readText()
            reader.close()
            JSONObject(jsonText)
        } catch (e: Exception) {
            e.printStackTrace()
            JSONObject() // Return empty JSON if failed
        }
    }
}