package com.example.weatherapiusingcoroutines.model.remote

import com.example.weatherapiusingcoroutines.model.remote.data.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("forecast?")
    suspend fun getWeather(@Query("q")city:String,@Query("appid")id:String): Response<WeatherResponse>

    companion object {
        fun getInstance(): ApiService = ApiClient.retrofit.create(ApiService::class.java)
    }
}
