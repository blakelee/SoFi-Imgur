package net.blakelee.model

import android.content.Context
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(config: ServiceConfig) {

    private val retrofit = buildRetrofit(config)

    private fun buildRetrofit(config: ServiceConfig): Retrofit {
        val builder = Retrofit.Builder()

        builder.baseUrl(config.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())

        val client = OkHttpClient.Builder()
        client.addInterceptor(AuthorizationInterceptor(config))

        builder.client(client.build())
        return builder.build()
    }

    val searchService = retrofit.create(SearchService::class.java)
}