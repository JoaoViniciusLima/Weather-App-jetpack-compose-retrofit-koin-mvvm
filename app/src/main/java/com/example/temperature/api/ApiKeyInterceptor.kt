package com.example.temperature.api
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    private val apiKey = "06c921750b9a82d8f5d1294e1586276f"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder()
            .addQueryParameter("appid", apiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}




