package com.example.weathertracker.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weathertracker.R
import com.example.weathertracker.api.data.DefaultAppContainer
import com.example.weathertracker.api.model.WeatherData
import com.example.weathertracker.viewmodel.WeatherTrackerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    navController: NavController
) {

    val cityName: MutableState<String> = remember { mutableStateOf("") }
    val appContainer = DefaultAppContainer()
    val viewModel = WeatherTrackerViewModel(appContainer.weatherTrackerRepository)
    val weatherData = viewModel.weatherData
    val displayWeatherScreen = viewModel.weatherScreenUpdate.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold { it -> Box { Modifier.padding(it)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 88.dp)
            ) {
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                    readOnly = false,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(20.dp),
                    value = cityName.value,
                    singleLine = true,
                    label = {Text (stringResource(id = R.string.search_location))},
                    onValueChange = {
                        cityName.value = it
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            if (cityName.value.isNotEmpty()) {
                                viewModel.getCityWeather(cityName.value)
                                focusManager.clearFocus()
                                viewModel.clearWeatherScreen()
                            }
                        }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = ""
                            )
                        }
                    }
                )
                if (cityName.value.isEmpty()) {
                    DisplayNoResult()
                    viewModel.clearWeatherScreen()
                }
                if (displayWeatherScreen.value) {
                    DisplayWeatherResult(weatherData.value, viewModel)
                }
            }
        }
    }
}

@Composable
fun DisplayNoResult() {
    Box (modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        contentAlignment = Alignment.Center) {
        Text(text = stringResource(id = R.string.no_city_selected),
            textAlign = TextAlign.Center,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold)
        Text(text = stringResource(id = R.string.please_search_city),
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 80.dp))
    }
}

@Composable
fun DisplayWeatherResult(weatherData: WeatherData?,
                         viewModel: WeatherTrackerViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp)
    ) {
        viewModel.getWeatherImage(weatherData)?.let {
            painterResource(
                id = it
            )
        }?.let {
            Image(modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .align(Alignment.CenterHorizontally),
                painter = it,
                contentDescription = "Weather Image")
        }
        Text(modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.city_name, weatherData?.location?.name.toString()),
            textAlign = TextAlign.Center,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold)
        Text(modifier = Modifier
            .padding(top = 10.dp).align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.temperature_fahrenheit, weatherData?.current?.temp_f.toString()),
            textAlign = TextAlign.Center,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold)
        DisplayWeatherInfo(weatherData)
    }
}

@Composable
fun DisplayWeatherInfo(weatherData: WeatherData?) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(top = 20.dp, start = 45.dp, end = 45.dp)
        .background(colorResource(R.color.light_gray),
                    shape = RoundedCornerShape(20.dp)),
        verticalAlignment = Alignment.CenterVertically
        ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stringResource(id = R.string.humidity, weatherData?.current?.humidity.toString()),
                textAlign = TextAlign.Center)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stringResource(id = R.string.uv_index, weatherData?.current?.uv.toString()),
                textAlign = TextAlign.Center)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stringResource(id = R.string.feels_like, weatherData?.current?.feelslike_f.toString()),
                textAlign = TextAlign.Center)
        }
    }
}

