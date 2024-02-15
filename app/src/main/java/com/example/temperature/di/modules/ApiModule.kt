package com.example.temperature.di.modules

import com.example.temperature.api.ApiKeyInterceptor
import com.example.temperature.api.RetrofitService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single {providerRetrofitService() }
}

fun providerRetrofitService(): RetrofitService{

    val baseUrl = "https://api.openweathermap.org/data/2.5/"

    val interceptor = ApiKeyInterceptor()

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build().create(RetrofitService::class.java)

}