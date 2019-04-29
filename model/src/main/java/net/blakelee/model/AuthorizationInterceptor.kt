package net.blakelee.model

import okhttp3.Interceptor
import okhttp3.Response

const val HEADER_AUTHORIZATION = "Authorization"

class AuthorizationInterceptor(private val config: ServiceConfig) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .addHeader(HEADER_AUTHORIZATION, config.clientId)
            .build()

        return chain.proceed(newRequest)
    }
}