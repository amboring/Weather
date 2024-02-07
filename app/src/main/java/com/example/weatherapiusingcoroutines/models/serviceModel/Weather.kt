package com.example.weatherapiusingcoroutines.models.serviceModel

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)