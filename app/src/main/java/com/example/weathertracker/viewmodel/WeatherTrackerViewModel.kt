package com.example.weathertracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertracker.R
import com.example.weathertracker.api.data.WeatherTrackerRepository
import com.example.weathertracker.api.model.WeatherData
import com.example.weathertracker.api.network.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherTrackerViewModel @Inject internal constructor (
    private val weatherServiceRepository: WeatherTrackerRepository
) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> = _weatherData
    private var weatherImagesMap = mapOf<Int, Int>()
    private val _weatherScreenUpdate = MutableStateFlow(false)
    val weatherScreenUpdate: MutableStateFlow<Boolean> = _weatherScreenUpdate

    init {
        weatherImagesData()
    }

    fun getCityWeather(cityName: String) {
        viewModelScope.launch {
            val result : ResultState = weatherServiceRepository.getCityWeather(cityName)
            when (result) {
                is ResultState.Success -> {
                    _weatherData.value = weatherServiceRepository.getWeatherData()
                    _weatherScreenUpdate.value = true
                }
                is ResultState.Error -> {
                    _weatherScreenUpdate.value = false
                }
                else -> {}
            }
        }
    }

    fun clearWeatherScreen() {
        _weatherScreenUpdate.value = false
    }

    private fun weatherImagesData() {
        weatherImagesMap = mapOf(1000 to R.drawable.sunny,
            1003 to R.drawable.partly_cloudy,
            1006 to R.drawable.cloudy,
            1009 to R.drawable.overcast,
            1030 to R.drawable.mist,
            1063 to R.drawable.patchy_rain,
            1066 to R.drawable.patchy_snow,
            1069 to R.drawable.patchy_sleet,
            1072 to R.drawable.patchy_freeze_drizzle,
            1087 to R.drawable.thundery_outbreak,
            1114 to R.drawable.blowing_snow,
            1117 to R.drawable.blizzard,
            1135 to R.drawable.fog,
            1147 to R.drawable.freezing_fog,
            1150 to R.drawable.patchy_light_drizzle,
            1153 to R.drawable.light_drizzle,
            1168 to R.drawable.freezing_drizzle,
            1171 to R.drawable.heavy_freezing_drizzle,
            1180 to R.drawable.patchy_light_rain,
            1183 to R.drawable.light_rain,
            1186 to R.drawable.moderate_rain_times,
            1189 to R.drawable.moderate_rain,
            1192 to R.drawable.heavy_rain_times,
            1195 to R.drawable.heavy_rain,
            1198 to R.drawable.light_freezing_rain,
            1201 to R.drawable.moderate_or_heavy_freezing_rain,
            1204 to R.drawable.light_sleet,
            1207 to R.drawable.moderate_heavy_sleet,
            1210 to R.drawable.patchy_light_snow,
            1213 to R.drawable.light_snow,
            1216 to R.drawable.patchy_moderate_snow,
            1219 to R.drawable.moderate_snow,
            1222 to R.drawable.patchy_heavy_snow,
            1225 to R.drawable.heavy_snow,
            1237 to R.drawable.ice_pellets,
            1240 to R.drawable.light_rain_shower,
            1243 to R.drawable.moderate_heavy_rain_shower,
            1246 to R.drawable.torrential_rain_shower,
            1249 to R.drawable.light_sleet_showers,
            1252 to R.drawable.moderate_heavy_sleet_shower,
            1255 to R.drawable.light_snow_showers,
            1258 to R.drawable.moderate_heavy_snow_showers,
            1261 to R.drawable.light_shower_ice_pellets,
            1264 to R.drawable.moderate_heavy_showers_ice_pellets,
            1273 to R.drawable.patchy_light_rain_thunder,
            1276 to R.drawable.moderate_heavy_rain_thunder,
            1279 to R.drawable.patchy_light_snow_thunder,
            1282 to R.drawable.moderate_heavy_snow_thunder)
    }

    fun getWeatherImage(data : WeatherData?) : Int? {
        return weatherImagesMap[data?.current?.condition?.code]
    }
}