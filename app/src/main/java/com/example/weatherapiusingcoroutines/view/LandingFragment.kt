package com.example.weatherapiusingcoroutines.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.al.weatherapiusingcoroutines.R
import com.al.weatherapiusingcoroutines.databinding.FragmentLayoutBinding
import com.example.weatherapiusingcoroutines.WeatherApp
import com.example.weatherapiusingcoroutines.composable.HasWeatherPage
import com.example.weatherapiusingcoroutines.di.ViewModelFactory
import com.example.weatherapiusingcoroutines.models.state.WeatherState
import com.example.weatherapiusingcoroutines.util.PermissionUtil
import com.example.weatherapiusingcoroutines.viewmodel.LandingViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import javax.inject.Inject

class LandingFragment : Fragment(R.layout.fragment_layout) {


    private var _fragmentBinding: FragmentLayoutBinding? = null
    private val fragmentBinding: FragmentLayoutBinding get() = _fragmentBinding!!
    private var promptedForLocationEnablement = false
    private var resolutionForResult: ActivityResultLauncher<IntentSenderRequest>? = null
    protected var locationCallback: LocationCallback? = null
    protected var fusedLocationClient: FusedLocationProviderClient? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val landingViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LandingViewModel::class.java]
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startLocationUpdates()
            } else {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    startLocationUpdates()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Location is off, showing last searched weather",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentBinding = FragmentLayoutBinding.inflate(inflater)

        fragmentBinding.fragmentContainer.setContent {
            LandingPage(WeatherState.Loading) {
                findNavController().navigate(LandingFragmentDirections.actionLandingFragmentToSearchFragment())
            }
        }
        (requireActivity().application as WeatherApp).appComponent.inject(this)
        landingViewModel.weatherLiveData.observe(viewLifecycleOwner) {
            fragmentBinding.fragmentContainer.setContent {
                LandingPage(it) {
                    findNavController().navigate(LandingFragmentDirections.actionLandingFragmentToSearchFragment())
                }
            }
        }

        if (GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(requireContext()) == ConnectionResult.SUCCESS
        ) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        }
        resolutionForResult =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    startLocationUpdates()
                } else {
                    landingViewModel.getLastSearchedWeather()
                }
            }
        if (!PermissionUtil.userGrantedLocationPermission(requireActivity())) {
            landingViewModel.getLastSearchedWeather()
            PermissionUtil.requestLocationPermission(requestPermission)
        } else {
            startLocationUpdatesIfLocationSettingsAreEnabled()
        }
        return fragmentBinding.root
    }

    private fun startLocationUpdatesIfLocationSettingsAreEnabled() {
        val request = getLocationRequest()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(request)

        LocationServices.getSettingsClient(requireActivity()).checkLocationSettings(builder.build())
            .addOnFailureListener {

                val exception = it as? ApiException
                when (exception?.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        val resolution = exception as? ResolvableApiException
                        if (resolution != null && !promptedForLocationEnablement) {
                            val intentSenderRequest =
                                IntentSenderRequest.Builder(exception.resolution).build()
                            runCatching {
                                resolutionForResult?.launch(intentSenderRequest)
                                promptedForLocationEnablement = true
                            }
                        }
                    }

                    else -> {
                        landingViewModel.getLastSearchedWeather()
                    }
                }
            }.addOnSuccessListener {
                stopLocationUpdates()
                startLocationUpdates()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        resolutionForResult?.unregister()
        resolutionForResult = null
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun getLocationRequest(): LocationRequest {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY, 1000000
        ).apply {
            setMinUpdateDistanceMeters(1000F)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
        }.build()
        return request
    }

    private fun startLocationUpdates() {
        val callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                landingViewModel.getDefaultLocation(
                    locationResult.lastLocation?.latitude ?: 0.0,
                    locationResult.lastLocation?.longitude ?: 0.0
                )
            }
        }
        locationCallback = callback
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission
            return
        }
        fusedLocationClient?.requestLocationUpdates(
            getLocationRequest(),
            callback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        val callback = locationCallback
        if (callback != null) {
            fusedLocationClient?.removeLocationUpdates(callback)
        }
    }

}

@Composable
fun LandingPage(state: WeatherState, onSearchCallback: (() -> Unit)? = null) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = stringResource(id = R.string.search_icon),
            modifier = Modifier
                .align(End)
                .padding(16.dp)
                .clickable {
                    onSearchCallback?.invoke()
                }
        )
        when (state) {
            is WeatherState.HasWeather -> {
                HasWeatherPage(state.weather)
            }

            WeatherState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

            is WeatherState.Error -> {
                val context = LocalContext.current
                Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

