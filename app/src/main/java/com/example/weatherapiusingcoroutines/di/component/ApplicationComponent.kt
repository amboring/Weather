package com.example.weatherapiusingcoroutines.di.component

import android.app.Application
import android.content.Context
import com.example.weatherapiusingcoroutines.di.module.ActivityModule
import com.example.weatherapiusingcoroutines.di.module.ApplicationModule
import com.example.weatherapiusingcoroutines.di.module.ApplicationViewModelModule
import com.example.weatherapiusingcoroutines.di.module.ViewModelModule
import com.example.weatherapiusingcoroutines.di.qualifier.ApplicationContext
import com.example.weatherapiusingcoroutines.service.ApiService
import com.example.weatherapiusingcoroutines.view.LandingFragment
import com.example.weatherapiusingcoroutines.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityModule::class,
        ApplicationModule::class,
        ViewModelModule::class,
        ApplicationViewModelModule::class
    ]
)
interface ApplicationComponent {
//    @ApplicationContext
//    fun context(): Context
    fun application(): Application
//    fun serviceProvider(): ServiceProvider
//
//    fun favDataBase(): FavDataBase
    fun service(): ApiService

}