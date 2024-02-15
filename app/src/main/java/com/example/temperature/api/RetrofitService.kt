package com.example.temperature.api

import com.example.temperature.models.WeatherResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("weather?units=metric&lang=pt_br")
    fun getWeatherData(
        @Query("q") city:String
    ): Call<WeatherResponse>

}


