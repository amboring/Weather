package com.example.weatherapiusingcoroutines.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.al.weatherapiusingcoroutines.databinding.ActivityMainBinding
import com.example.weatherapiusingcoroutines.models.state.WeatherForDisplay
import com.example.weatherapiusingcoroutines.service.ApiService
import com.example.weatherapiusingcoroutines.service.Repository
import com.example.weatherapiusingcoroutines.viewmodel.WeatherViewModel
import com.example.weatherapiusingcoroutines.viewmodel.WeatherViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var weatherViewModelFactory: WeatherViewModelFactory
    private lateinit var listAdapter: RecycleViewAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherViewModelFactory = WeatherViewModelFactory(Repository(ApiService.getInstance()))
        weatherViewModel =
            ViewModelProvider(this, weatherViewModelFactory)[WeatherViewModel::class.java]
        binding.btSearch.setOnClickListener { searchWeather() }
        binding.btRefresh.setOnClickListener { searchWeather() }
        setUpObservers()
    }

    @SuppressLint("ServiceCast")
    private fun searchWeather() {
        val city = binding.etCityInput.text.toString().trim()
        if (city.isNotEmpty()) {
            weatherViewModel.loadWeather(city)
            val view = this.currentFocus
            view?.let {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("Please enter a valid city.")
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }
    }

    private fun setUpObservers() {
        weatherViewModel.weatherLiveData.observe(this) {
            setResult(it)
            Log.i("alalal","$it")
        }

        weatherViewModel.error.observe(this) {
            Log.i("alalal",it)
            binding.recyclerView.adapter = null
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        weatherViewModel.processing.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setResult(weathers: List<WeatherForDisplay>) {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        listAdapter = RecycleViewAdaptor(weathers)
        binding.recyclerView.adapter = listAdapter
    }
}