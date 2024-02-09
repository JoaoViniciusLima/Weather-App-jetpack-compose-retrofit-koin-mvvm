package com.example.temperature.models

data class WeatherResponse (
    val weather: List<Weather>,
    val main: Main,
    val sys: Sys,
    val wind: Wind
)

data class Weather(
    val description: String
)

data class Main(
    val temp: Double,
    val pressure: String,
    val humidity: String
)

data class Sys(
    val sunrise: Long,
    val sunset: Long
)

data class Wind(
    val speed: String
)
