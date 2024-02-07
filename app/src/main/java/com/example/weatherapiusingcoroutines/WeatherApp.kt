package com.example.weatherapiusingcoroutines

import android.app.Application
import com.example.weatherapiusingcoroutines.di.component.ApplicationComponent
import com.example.weatherapiusingcoroutines.di.component.DaggerApplicationComponent
import com.example.weatherapiusingcoroutines.di.module.ApplicationModule


class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
    companion object {
        lateinit var appComponent: ApplicationComponent
    }
}