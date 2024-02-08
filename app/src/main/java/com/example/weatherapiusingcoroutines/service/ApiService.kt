package com.example.weatherapiusingcoroutines.service

import com.example.weatherapiusingcoroutines.models.response.LocalWeather
import com.example.weatherapiusingcoroutines.models.forecastResponse.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather?")
    suspend fun getLocalWeather(
        @Query("q") city: String,
        @Query("appid") id: String
    ): Response<LocalWeather>

    @GET("forecast?")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appid") id: String
    ): Response<WeatherResponse>

    @GET("weather?")
    suspend fun getLatitudeWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") id: String
    ): Response<LocalWeather>

}
