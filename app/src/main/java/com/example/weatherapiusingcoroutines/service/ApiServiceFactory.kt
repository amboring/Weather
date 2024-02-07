package com.example.weatherapiusingcoroutines.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceFactory {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    fun makeService(): ApiService {
        return makeServiceHelper()
    }

    private fun makeServiceHelper(): ApiService {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(HTTPLogger.getLogger())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}

object HTTPLogger {
    fun getLogger(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
}
