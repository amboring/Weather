package com.example.weatherapiusingcoroutines.models.state

sealed class WeatherState {
    object Loading : WeatherState()
    data class Error(val msg: String) : WeatherState()
    data class HasWeather(val weather: LandingWeather) : WeatherState()
}