package net.blakelee.sofiandroidchallenge

import android.app.Application
import net.blakelee.model.RetrofitClient

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val config = DefaultConfig()
        val client = RetrofitClient(config)
        val modelProvider = AppModelProvider(client)
        Provider.onCreate(modelProvider)
    }
}