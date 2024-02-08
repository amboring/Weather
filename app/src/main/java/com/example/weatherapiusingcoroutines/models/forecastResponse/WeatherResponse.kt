package com.example.weatherapiusingcoroutines.models.forecastResponse

data class WeatherResponse(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<TimeStamp?>?,
    val message: Int?
)