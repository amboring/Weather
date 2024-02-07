package com.example.weatherapiusingcoroutines.di.component

import com.example.weatherapiusingcoroutines.di.module.ActivityModule
import com.example.weatherapiusingcoroutines.di.module.ApplicationModule
import com.example.weatherapiusingcoroutines.di.module.ApplicationViewModelModule
import com.example.weatherapiusingcoroutines.di.module.ViewModelModule
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
])

interface ViewComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(landingFragment: LandingFragment)
}