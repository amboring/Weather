package com.example.weatherapiusingcoroutines.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.al.weatherapiusingcoroutines.databinding.ItemWeatherBinding
import com.example.weatherapiusingcoroutines.models.state.WeatherForDisplay


class RecycleViewAdaptor(private val weathers: List<WeatherForDisplay>) :
    RecyclerView.Adapter<WeatherItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemWeatherBinding.inflate(layoutInflater, parent, false)
        return WeatherItemHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WeatherItemHolder, position: Int) {
        val weather = weathers[position]
        holder.bind(weather)
    }

    override fun getItemCount() = weathers.size
}


class WeatherItemHolder(
    val binding: ItemWeatherBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(weather: WeatherForDisplay) {
        binding.tvDate.text = weather.date
        binding.tvFeelsLike.text = "${weather.feels_like?.toInt()} C"
        binding.tvHumidity.text = weather.humidity.toString()
        binding.tvTemp.text ="${weather.temp?.toInt()} C"
        binding.tvTempMax.text = "${weather.temp_max?.toInt()} C"
        binding.tvTempMin.text = "${weather.temp_min?.toInt()} C"

    }
}