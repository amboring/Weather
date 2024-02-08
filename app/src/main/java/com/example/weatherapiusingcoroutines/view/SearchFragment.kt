package com.example.weatherapiusingcoroutines.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.al.weatherapiusingcoroutines.R
import com.al.weatherapiusingcoroutines.databinding.FragmentSearchBinding
import com.example.weatherapiusingcoroutines.WeatherApp
import com.example.weatherapiusingcoroutines.composable.WeatherList
import com.example.weatherapiusingcoroutines.di.ViewModelFactory
import com.example.weatherapiusingcoroutines.viewmodel.WeatherViewModel
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val weatherViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[WeatherViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentSearchBinding.inflate(layoutInflater)

        (requireActivity().application as WeatherApp).appComponent.inject(this)

        binding.btSearch.setOnClickListener { searchWeather() }
        binding.btRefresh.setOnClickListener { searchWeather() }
        binding.backButton.setOnClickListener { findNavController().navigateUp() }
        binding.clearButton.setOnClickListener { binding.etCityInput.setText("") }
        setUpObservers()
        weatherViewModel.getLastSearchedWeather()
        return binding.root
    }

    private fun setUpObservers() {
        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner) {
            binding.recyclerView.setContent {
                WeatherList(it)
            }
        }

        weatherViewModel.error.observe(viewLifecycleOwner) {
            Log.i("alalal", it)
            binding.recyclerView.setContent { }
            binding.progressBar.visibility = View.GONE
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }

        weatherViewModel.processing.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun searchWeather() {
        val city = binding.etCityInput.text.toString().trim()
        Log.i("alalal", city)
        weatherViewModel.loadWeather(city)
        if (city.isNotEmpty()) {
            weatherViewModel.loadWeather(city)
            val view = requireActivity().currentFocus
            view?.let {
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } else {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Error")
            builder.setMessage("Please enter a valid city.")
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }
    }
}