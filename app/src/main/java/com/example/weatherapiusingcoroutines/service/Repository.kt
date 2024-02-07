package com.example.weatherapiusingcoroutines.service

import com.example.weatherapiusingcoroutines.models.state.WeatherForDisplay
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
                val format = SimpleDateFormat("MM/dd/yyyy HH:mm:ss ", Locale.US)
                val date = it?.dt?.let {
                    val dateTime = Date(it)
                    format.format(dateTime)
                }.orEmpty()

                val feelsLike = it?.main?.feels_like?.let { it - KCDifference}
                val humidity = it?.main?.humidity
                val temp = it?.main?.temp?.let {  it - KCDifference}
                val tempMax = it?.main?.temp_max?.let {  it - KCDifference}
                val tempMin = it?.main?.temp_min?.let {  it - KCDifference}
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