package com.example.weatherapiusingcoroutines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapiusingcoroutines.model.remote.Repository

class WeatherViewModelFactory constructor(private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            WeatherViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("View Model not found!!")
        }
    }
}