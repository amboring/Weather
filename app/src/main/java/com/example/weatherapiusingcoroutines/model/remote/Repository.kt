package com.example.weatherapiusingcoroutines.model.remote

import com.example.weatherapiusingcoroutines.model.remote.data.WeatherForDisplay
import java.text.SimpleDateFormat
import java.util.*

class Repository(private val apiService: ApiService) {

    suspend fun getWeather(city: String): List<WeatherForDisplay> {
        if (city.isNullOrBlank()) {
            return listOf()
        }
        val response = apiService.getWeather(city, id)
        val result = mutableListOf<WeatherForDisplay>()
        if (response.isSuccessful) {
            val list = response.body()?.list
            list?.forEach { it ->
                val dateTime = Date(it.dt)
                val format = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.US)
                val date = format.format(dateTime)
                val feelsLike = it.main.feels_like - KCDifference
                val humidity = it.main.humidity
                val temp = it.main.temp - KCDifference
                val tempMax = it.main.temp_max - KCDifference
                val tempMin = it.main.temp_min - KCDifference
                result.add(
                    WeatherForDisplay(date, feelsLike, humidity, temp, tempMax, tempMin)
                )
            }
        }
        return result.toList()
    }

    companion object {
        const val KCDifference = 273
        private const val id = "b48bdf7e19ffd2593d0fe4a1daf0ebc4"
    }
}