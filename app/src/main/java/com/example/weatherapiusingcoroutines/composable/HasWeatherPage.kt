package com.example.weatherapiusingcoroutines.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.al.weatherapiusingcoroutines.R
import com.example.weatherapiusingcoroutines.models.response.Main
import com.example.weatherapiusingcoroutines.models.response.Weather
import com.example.weatherapiusingcoroutines.models.state.LandingWeather
import com.example.weatherapiusingcoroutines.util.WeatherIconUtil

@Composable
fun HasWeatherPage(weather: LandingWeather, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = weather.location,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = FontConfiguration.headline1
        )
        AsyncImage(
            model = WeatherIconUtil.getWeatherIconUrl(weather.weather[0].icon),
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
                .align(Alignment.CenterHorizontally),
            contentDescription = stringResource(id = R.string.weather_icon)
        )
        weather.temperature?.temp?.let {
            Text(
                text = "$it",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = FontConfiguration.headline1
            )
        }
        Text(
            text = weather.weather[0].description,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = FontConfiguration.headline3
        )
        Row(
            modifier
                .align(CenterHorizontally)
                .padding(20.dp)
        ) {
            weather.temperature?.temp_max?.let {
                Text(
                    text = "H:$it",
                    style = FontConfiguration.headline3,
                )
            }
            Spacer(modifier = Modifier.width(50.dp))
            weather.temperature?.temp_min?.let {
                Text(
                    text = "L:$it",
                    style = FontConfiguration.headline3
                )
            }
        }
    }
}

@Preview
@Composable
fun HasWeatherPagePreview() {
    HasWeatherPage(
        LandingWeather(
            "New York",
            listOf(Weather("clear sky", "01d", 800, "Clear")),
            Main(270.83, 62, 1028, 275.06, 277.02, 272.66)
        )
    )
}