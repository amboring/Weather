package com.example.weatherapiusingcoroutines.util

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object PermissionUtil {
    fun requestLocationPermission(request: ActivityResultLauncher<String>) {
        request.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun userGrantedLocationPermission(hostActivity: FragmentActivity): Boolean {
        val hasCoarsePermission = ContextCompat.checkSelfPermission(
            hostActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val hasFinePermission = ContextCompat.checkSelfPermission(
            hostActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return hasCoarsePermission == PackageManager.PERMISSION_GRANTED || hasFinePermission == PackageManager.PERMISSION_GRANTED
    }
}