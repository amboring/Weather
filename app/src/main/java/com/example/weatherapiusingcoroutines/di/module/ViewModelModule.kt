package com.example.weatherapiusingcoroutines.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapiusingcoroutines.di.ViewModelFactory
import com.example.weatherapiusingcoroutines.di.qualifier.ViewModelKey
import com.example.weatherapiusingcoroutines.viewmodel.LandingViewModel
import com.example.weatherapiusingcoroutines.viewmodel.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@Module
abstract class ApplicationViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun provideWeatherViewModel(viewModel: WeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LandingViewModel::class)
    abstract fun provideLandingViewModel(viewModel: LandingViewModel): ViewModel
}
