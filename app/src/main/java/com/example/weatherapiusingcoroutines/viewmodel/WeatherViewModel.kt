package com.example.weatherapiusingcoroutines.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapiusingcoroutines.service.Repository
import com.example.weatherapiusingcoroutines.models.state.WeatherForDisplay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val weatherLiveData = MutableLiveData<List<WeatherForDisplay>>()
    val error = MutableLiveData<String>()
    val processing = MutableLiveData<Boolean>()

    fun loadWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
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
                Log.i("alalal",e.message.orEmpty())
                error.postValue(e.message)
                e.printStackTrace()
                processing.postValue(false)
            }
        }
    }
}