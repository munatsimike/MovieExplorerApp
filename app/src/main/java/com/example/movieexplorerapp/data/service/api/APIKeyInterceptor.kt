package com.example.movieexplorerapp.data.service.api

import okhttp3.Interceptor
import okhttp3.Response

/**
 * The api key interceptor is responding for appending the api key to HTTP requests.
 * The class takes the api key as the argument add appends is as a query parameter to the original URL.
 *
 */
class APIKeyInterceptor(private val apiKeyProvider: APIKeyProvider) : Interceptor {
    private val apiKey by lazy { apiKeyProvider.getKey() }
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", apiKey.value)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}