package com.example.temperature.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt
data class WeatherData(
    var temperature: String? = null,
    var sunset: String? = null,
    var sunrise: String? = null,
    var humidity: String? = null,
    var pressure: String? = null,
    var wind: String? = null,
    var description: String? = null,
    var weatherId: Int? = null

){
    fun hasNullAttributes(): Boolean {
        return temperature == null ||
                sunset == null ||
                sunrise == null ||
                humidity == null ||
                pressure == null ||
                wind == null ||
                description == null ||
                weatherId == null
    }

    constructor(weatherResponse: WeatherResponse) :
            this(
                weatherResponse.main.temp?.let{
                weatherResponse.main.temp.roundToInt().toString()
                },
                weatherResponse.sys.sunset?.let{
                    timestampToTime(weatherResponse.sys.sunset)
                },
                weatherResponse.sys.sunrise?.let{
                    timestampToTime(weatherResponse.sys.sunrise!!)
                },
                weatherResponse.main.humidity?.let{
                weatherResponse.main.humidity
                },
                weatherResponse.main.pressure?.let{
                weatherResponse.main.pressure
                },
                weatherResponse.wind.speed?.let{
                weatherResponse.wind.speed
                },
                weatherResponse.weather[0].description?.let{
                    weatherResponse.weather[0].description
                },
                weatherResponse.weather[0].id?.let{
                    weatherResponse.weather[0].id
                }
            )



}

fun timestampToTime(weatherData: Long): String? {
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    dateFormat.timeZone = TimeZone.getTimeZone("GMT-3")

    return dateFormat.format(weatherData!! * 1000)
}