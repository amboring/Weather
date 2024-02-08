package com.example.weatherapiusingcoroutines.models.state

import com.example.weatherapiusingcoroutines.models.response.Main
import com.example.weatherapiusingcoroutines.models.response.Weather

data class LandingWeather(
    val location: String,
    val weather: List<Weather>,
    val temperature: Main?,
)
