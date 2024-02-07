package com.example.weatherapiusingcoroutines.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapiusingcoroutines.models.serviceModel.Weather
import com.example.weatherapiusingcoroutines.models.state.WeatherState
import com.example.weatherapiusingcoroutines.service.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LandingViewModel(private val repository: Repository):ViewModel() {
    private val _weatherLiveData:MutableLiveData<WeatherState> = MutableLiveData()
    val weatherLiveData: LiveData<WeatherState> =_weatherLiveData


    fun getWeather(city:String){
        _weatherLiveData.postValue(WeatherState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = repository.getWeather(city)
                if (list.isEmpty()) {
                    _weatherLiveData.postValue(WeatherState.Error("No data found."))
                    return@launch
                }
                //todo: get the right data
                _weatherLiveData.postValue(WeatherState.HasWeather(list[0]))

            } catch (e: Exception) {
                _weatherLiveData.postValue(WeatherState.Error(e.message.orEmpty()))
                e.printStackTrace()
            }
        }
    }
}