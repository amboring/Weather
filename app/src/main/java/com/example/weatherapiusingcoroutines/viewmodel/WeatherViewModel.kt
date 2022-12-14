package com.example.weatherapiusingcoroutines.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapiusingcoroutines.model.remote.Repository
import com.example.weatherapiusingcoroutines.model.remote.data.WeatherForDisplay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: Repository) : ViewModel() {
    val weatherLiveData = MutableLiveData<List<WeatherForDisplay>>()
    val error = MutableLiveData<String>()
    val processing = MutableLiveData<Boolean>()

    fun loadWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO)
        {
            try {
                processing.postValue(true)
                val list = repository.getWeather(city)
                if (list.isEmpty()) {
                    error.postValue("No data found.")
                    return@launch
                }
                weatherLiveData.postValue(list)
                processing.postValue(false)

            } catch (e: Exception) {
                error.postValue("Technical error, please try again later.")
                e.printStackTrace()
                processing.postValue(false)
            }
        }
    }
}