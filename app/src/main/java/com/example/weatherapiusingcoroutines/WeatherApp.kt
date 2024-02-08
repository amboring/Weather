package com.example.weatherapiusingcoroutines

import android.app.Application
import com.example.weatherapiusingcoroutines.di.component.DaggerApplicationComponent
import com.example.weatherapiusingcoroutines.di.module.ApplicationModule


class WeatherApp : Application() {
    val appComponent = DaggerApplicationComponent
        .builder()
        .applicationModule(ApplicationModule(this))
        .build()


}