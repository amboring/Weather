package com.example.weatherapiusingcoroutines.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
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

    @RequiresApi(33)
    fun userGrantedNotificationPermission(hostActivity: FragmentActivity): Boolean {
        val hasAccessNotification = ContextCompat.checkSelfPermission(
            hostActivity,
            Manifest.permission.POST_NOTIFICATIONS
        )
        return hasAccessNotification == PackageManager.PERMISSION_GRANTED
    }

    fun showPermissionRationaleToast(fragmentContext: Context, message: String) {
        Toast.makeText(
            fragmentContext,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}