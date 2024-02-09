package com.example.temperature.repositores

import com.example.temperature.api.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService ) {

    fun getWeatherData(city: String ) = retrofitService.getWeatherData(city)

}