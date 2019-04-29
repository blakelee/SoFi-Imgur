package net.blakelee.sofiandroidchallenge.app

import android.app.Application
import net.blakelee.model.services.client.RetrofitClient
import net.blakelee.sofiandroidchallenge.config.DefaultConfig
import net.blakelee.sofiandroidchallenge.providers.AppModelProvider
import net.blakelee.sofiandroidchallenge.providers.Provider

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val config = DefaultConfig()
        val client = RetrofitClient(config)
        val modelProvider = AppModelProvider(client)
        Provider.onCreate(modelProvider)
    }
}