package com.example.weatherapiusingcoroutines.util

class WeatherIconUtil {
    companion object {
        fun getWeatherIconUrl(icon: String): String {
            return "https://openweathermap.org/img/wn/${icon}@2x.png"
        }
    }
}