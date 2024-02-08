package com.example.weatherapiusingcoroutines.view

import android.os.Bundle
import android.util.Log
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
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import javax.inject.Inject

class LandingFragment : Fragment(R.layout.fragment_layout) {


    private var _fragmentBinding: FragmentLayoutBinding? = null
    private val fragmentBinding: FragmentLayoutBinding get() = _fragmentBinding!!
    private var promptedForLocationEnablement = false
    private var resolutionForResult: ActivityResultLauncher<IntentSenderRequest>? = null
    protected var locationCallback: LocationCallback? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val landingViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LandingViewModel::class.java]
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                landingViewModel.getWeather("")
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

        Log.i("alalal", "permission granted")

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
                                landingViewModel.getLastSearchedWeather()
                            }
                        }
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        Log.i("alalal", "permission SETTINGS_CHANGE_UNAVAILABLE")
                    }

                    else -> {
                        Log.i("alalal", "permission other")
                        //todo: verify if the case[permission NOT granted] is matched here
                        landingViewModel.getLastSearchedWeather()
                    }
                }
            }.addOnSuccessListener {
                startLocationUpdates()
            }
    }

    private fun getLocationRequest(): LocationRequest {
        val request = LocationRequest.create()
        request.numUpdates = 10
        request.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        request.maxWaitTime = 10000
        return request
    }

    private fun startLocationUpdates() {
        Log.i("alalal", "startLocationUpdates")
        val callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                landingViewModel.getDefaultLocation(
                    locationResult.lastLocation?.latitude ?: 0.0,
                    locationResult.lastLocation?.longitude ?: 0.0
                )
            }
        }

        locationCallback = callback
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

