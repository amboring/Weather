package com.example.weatherapiusingcoroutines.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.al.weatherapiusingcoroutines.R
import com.example.weatherapiusingcoroutines.models.state.WeatherForDisplay
import com.example.weatherapiusingcoroutines.util.WeatherIconUtil

@Composable
fun WeatherList(weathers: List<WeatherForDisplay>) {
    LazyColumn {
        items(weathers) {
            WeatherItem(it)
        }
    }
}

@Composable
fun WeatherItem(weather: WeatherForDisplay) {
    Row(modifier = Modifier.padding(16.dp)) {
        AsyncImage(
            model = WeatherIconUtil.getWeatherIconUrl(weather.icon ?: ""),
            modifier = Modifier
                .height(90.dp)
                .width(90.dp),
            contentDescription = stringResource(id = R.string.weather_icon)
        )
        Column {
            Text(text = stringResource(id = R.string.date))
            Text(text = stringResource(id = R.string.feels_like))
            Text(text = stringResource(id = R.string.humidity))
            Text(text = stringResource(id = R.string.current_temp))
            Text(text = stringResource(id = R.string.high))
            Text(text = stringResource(id = R.string.Low))
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            Text(text = weather.date ?: "")
            Text(text = "${weather.feels_like}")
            Text(text = "${weather.humidity}")
            Text(text = "${weather.temp}")
            Text(text = "${weather.temp_max}")
            Text(text = "${weather.temp_min}")
        }
    }
}
