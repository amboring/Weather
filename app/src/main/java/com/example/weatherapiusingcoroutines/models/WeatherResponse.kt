package com.example.weatherapiusingcoroutines.models

data class WeatherResponse(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<TimeStamp?>?,
    val message: Int?
)