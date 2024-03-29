package com.example.weatherapiusingcoroutines.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapiusingcoroutines.models.state.WeatherState
import com.example.weatherapiusingcoroutines.service.Repository
import com.example.weatherapiusingcoroutines.util.DatastoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class LandingViewModel @Inject constructor(
    private val repository: Repository,
    private val datastoreManager: DatastoreManager
) : ViewModel() {
    private val _weatherLiveData: MutableLiveData<WeatherState> = MutableLiveData()
    val weatherLiveData: LiveData<WeatherState> = _weatherLiveData

    fun getDefaultLocationWeather(lat: Double, lon: Double) {
        _weatherLiveData.postValue(WeatherState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weather = repository.getLatitudeWeather(lat, lon)
                if (weather == null) {
                    _weatherLiveData.postValue(WeatherState.Error("No data found."))
                    return@launch
                }
                _weatherLiveData.postValue(WeatherState.HasWeather(weather))

            } catch (e: Exception) {
                _weatherLiveData.postValue(WeatherState.Error(e.message.orEmpty()))
                e.printStackTrace()
            }
        }
    }

    fun getLastSearchedWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            datastoreManager.getUserLatestSearch().firstOrNull() ?.let{
                if(it.isNotEmpty()) getWeather(it)
            }
        }
    }

    private fun getWeather(city: String) {
        _weatherLiveData.postValue(WeatherState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weather = repository.getLocalWeather(city)
                if (weather == null) {
                    _weatherLiveData.postValue(WeatherState.Error("No data found."))
                    return@launch
                }
                _weatherLiveData.postValue(WeatherState.HasWeather(weather))
            } catch (e: Exception) {
                _weatherLiveData.postValue(WeatherState.Error(e.message.orEmpty()))
                e.printStackTrace()
            }
        }
    }
}