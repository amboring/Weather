package com.example.weatherapiusingcoroutines.models.serviceModel

data class TimeStamp(
    val clouds: Clouds?,
    val dt: Long?,
    val dt_txt: String?,
    val main: Main?,
    val pop: Double?,
    val sys: Sys?,
    val visibility: Int?,
    val weather: List<Weather?>?,
    val wind: Wind?
)