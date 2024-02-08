package com.example.weatherapiusingcoroutines.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapiusingcoroutines.models.state.WeatherForDisplay
import com.example.weatherapiusingcoroutines.service.Repository
import com.example.weatherapiusingcoroutines.util.DatastoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val repository: Repository,
    private val datastoreManager: DatastoreManager
) : ViewModel() {
    val weatherLiveData = MutableLiveData<List<WeatherForDisplay>>()
    val error = MutableLiveData<String>()
    val processing = MutableLiveData<Boolean>()

    fun getLastSearchedWeather() {
        processing.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            loadWeather(datastoreManager.getUserLatestSearch().firstOrNull() ?: "")
        }
    }

    fun loadWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                processing.postValue(true)
                val list = repository.getWeatherList(city)
                if (list.isEmpty()) {
                    error.postValue("No data found.")
                    return@launch
                } else {
                    datastoreManager.setUserLatestSearch(city)
                }
                weatherLiveData.postValue(list)
                processing.postValue(false)

            } catch (e: Exception) {
                Log.i("alalal", e.message.orEmpty())
                error.postValue(e.message)
                processing.postValue(false)
            }
        }
    }
}