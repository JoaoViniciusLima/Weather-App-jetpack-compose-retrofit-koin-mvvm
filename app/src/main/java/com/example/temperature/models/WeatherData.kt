package com.example.temperature.models

data class WeatherData (
    var temperature: Double? = null,
    var sunset: Long? = null,
    var sunrise: Long? = null,
    var humidity: String? = null,
    var pressure: String? = null,
    var wind: String? = null,
    var description: String? = null

){
    fun setData(weatherResponse: WeatherResponse): WeatherData {
        temperature = weatherResponse.main.temp
        sunset = weatherResponse.sys.sunset
        sunrise = weatherResponse.sys.sunrise
        humidity = weatherResponse.main.humidity
        pressure = weatherResponse.main.pressure
        wind = weatherResponse.wind.speed
        description = weatherResponse.weather[0].description

        return this

    }
}
