package com.karthik.zohotasknewsapp.api

import okhttp3.Interceptor
import okhttp3.Response

class MyRetrofitNetworkInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val reqWithAuth =
            chain.request().newBuilder().header("Authorisation", "Bearer $token").build()
        return chain.proceed(reqWithAuth)
    }
}