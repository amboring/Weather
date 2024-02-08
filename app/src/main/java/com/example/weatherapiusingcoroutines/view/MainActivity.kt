package com.example.weatherapiusingcoroutines.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.al.weatherapiusingcoroutines.R
import com.al.weatherapiusingcoroutines.databinding.ActivityMainBinding
import com.example.weatherapiusingcoroutines.di.component.DaggerApplicationComponent
import com.example.weatherapiusingcoroutines.di.module.ApplicationModule


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(application))
            .build()
            .inject(this)
        setContentView(binding.root)

        val navHostFragment = binding.navHostFragment.getFragment<NavHostFragment>()
        kotlin.runCatching {
            navHostFragment.navController.graph =
                navHostFragment.navController.navInflater.inflate(R.navigation.navigation_main)
        }
    }

}