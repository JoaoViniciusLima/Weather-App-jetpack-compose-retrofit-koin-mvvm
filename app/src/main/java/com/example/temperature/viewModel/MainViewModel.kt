package com.example.temperature.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.temperature.models.WeatherData
import com.example.temperature.models.WeatherResponse
import com.example.temperature.repositores.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel(){

    val weatherData = MutableLiveData<WeatherData>()
    val cityName = MutableLiveData<String>()
    val estateName = MutableLiveData<String>()
    val networkState = MutableLiveData<String>()

    fun setLocation(city: String,estate: String){
        cityName.postValue(city)
        estateName.postValue(estate)
    }

    fun getWeatherData(city: String){
        val request = repository.getWeatherData(city.replaceFirstChar { it.lowercase() })
        request.enqueue(object : Callback<WeatherResponse>{

            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val weatherDataObject = WeatherData(response.body()!!)
                if (!weatherDataObject.hasNullAttributes()){
                    networkState.postValue("success")
                    weatherData.postValue(weatherDataObject)
                } else{
                    getWeatherData(city)
                }

            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                networkState.postValue("error")
            }

        }
        )
    }
}