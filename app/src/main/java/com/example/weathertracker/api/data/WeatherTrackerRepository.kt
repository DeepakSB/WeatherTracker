package com.example.weathertracker.api.data

import com.example.weathertracker.api.model.WeatherData
import com.example.weathertracker.api.network.ApiService
import com.example.weathertracker.api.network.ResultState

interface WeatherTrackerRepository {
    suspend fun getCityWeather(cityName: String): ResultState
    fun getWeatherData() : WeatherData?
}

class WeatherServiceRepositoryImpl(private val apiService: ApiService
) : WeatherTrackerRepository {
    private var weatherData: WeatherData? = null
    override suspend fun getCityWeather(cityName: String): ResultState {
        try {
            val response = apiService.getCityWeather(cityName = cityName)
            if (response.location.name.isNotEmpty()) {
                weatherData = response
                return ResultState.Success(response)
            }
            return ResultState.Error("City not found")
        } catch (e : Exception) {
            return ResultState.Error("City not found")
        }
    }

    override fun getWeatherData() : WeatherData? {
        return weatherData
    }

}