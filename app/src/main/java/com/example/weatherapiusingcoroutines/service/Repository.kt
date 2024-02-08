package com.example.weatherapiusingcoroutines.service

import com.example.weatherapiusingcoroutines.models.state.LandingWeather
import com.example.weatherapiusingcoroutines.models.state.WeatherForDisplay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Repository(private val apiService: ApiService) {

    suspend fun getLatitudeWeather(lat: Double, lon: Double): LandingWeather? {
        val response = apiService.getLatitudeWeather(lat, lon, id).body() ?: return null
        return LandingWeather(
            location = response?.name.orEmpty(),
            weather = response?.weather?.filterNotNull() ?: listOf(),
            temperature = response?.main
        )
    }

    suspend fun getLocalWeather(city: String?): LandingWeather? {
        if (city.isNullOrBlank()) {
            return null
        }
        val response = apiService.getLocalWeather(city, id).body() ?: return null
        return LandingWeather(
            location = response?.name.orEmpty(),
            weather = response?.weather?.filterNotNull() ?: listOf(),
            temperature = response?.main
        )
    }

    suspend fun getWeatherList(city: String): List<WeatherForDisplay> {
        if (city.isNullOrBlank()) {
            return listOf()
        }

        val response = apiService.getForecast(city, id)
        val result = mutableListOf<WeatherForDisplay>()
        if (response.isSuccessful) {
            val list = response.body()?.list
            list?.forEach { it ->
                val format = SimpleDateFormat("MM/dd/yyyy HH:mm:ss ", Locale.US)
                val date = it?.dt?.let {
                    val dateTime = Date(it)
                    format.format(dateTime)
                }.orEmpty()

                val feelsLike = it?.main?.feels_like?.let { it - KCDifference }?.toInt()
                val humidity = it?.main?.humidity
                val temp = it?.main?.temp?.let { it - KCDifference }?.toInt()
                val tempMax = it?.main?.temp_max?.let { it - KCDifference }?.toInt()
                val tempMin = it?.main?.temp_min?.let { it - KCDifference }?.toInt()
                val icon = it?.weather?.get(0)?.icon
                result.add(
                    WeatherForDisplay(date, icon, feelsLike, humidity, temp, tempMax, tempMin)
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