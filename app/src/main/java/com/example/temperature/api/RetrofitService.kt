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


    companion object{
        private val retrofitService : RetrofitService by lazy {

            val baseUrl = "https://api.openweathermap.org/data/2.5/"

            val interceptor = ApiKeyInterceptor()

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            retrofit.create(RetrofitService::class.java)
        }

        fun getInstance() : RetrofitService{
            return retrofitService
        }
    }

}


