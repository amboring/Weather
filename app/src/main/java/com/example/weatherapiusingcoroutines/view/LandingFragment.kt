package com.example.weatherapiusingcoroutines.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.al.weatherapiusingcoroutines.R
import com.al.weatherapiusingcoroutines.databinding.FragmentLayoutBinding
import com.example.weatherapiusingcoroutines.di.ViewModelFactory
import com.example.weatherapiusingcoroutines.viewmodel.WeatherViewModel
import javax.inject.Inject

class LandingFragment: Fragment(R.layout.fragment_layout){


    private var _fragmentBinding: FragmentLayoutBinding? = null
    private val fragmentBinding: FragmentLayoutBinding get() = _fragmentBinding!!

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
        _fragmentBinding = FragmentLayoutBinding.inflate(inflater)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}

@Composable
fun LandingPage(){

}

