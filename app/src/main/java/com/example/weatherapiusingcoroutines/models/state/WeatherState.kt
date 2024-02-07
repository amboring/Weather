package com.example.weatherapiusingcoroutines.models.state

import com.example.weatherapiusingcoroutines.models.serviceModel.Weather

sealed class WeatherState {
    object Loading: WeatherState()
    data class Error(val msg: String): WeatherState()
    data class HasWeather(val weather: WeatherForDisplay): WeatherState()
}