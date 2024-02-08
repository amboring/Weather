package com.example.weatherapiusingcoroutines.models.state

data class WeatherForDisplay(
    val date: String?,
    val icon: String?,
    val feels_like: Int?,
    val humidity: Int?,
    val temp: Int?,
    val temp_max: Int?,
    val temp_min: Int?
)
