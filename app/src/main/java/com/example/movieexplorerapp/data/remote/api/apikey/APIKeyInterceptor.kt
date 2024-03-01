package com.example.movieexplorerapp.data.remote.api.apikey

import okhttp3.Interceptor
import okhttp3.Response

/**
 * The api key interceptor is responding for appending the api key to HTTP requests.
 * The class takes the api key as the argument add appends is as a query parameter to the original URL.
 *
 */
class APIKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}