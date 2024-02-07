package com.example.weatherapiusingcoroutines.models.state

data class WeatherForDisplay(
    val date: String?,
    val feels_like: Double?,
    val humidity: Int?,
    val temp: Double?,
    val temp_max: Double?,
    val temp_min: Double?
)
