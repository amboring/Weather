package com.example.weatherapiusingcoroutines.models.forecastResponse

import com.example.weatherapiusingcoroutines.models.response.Weather

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