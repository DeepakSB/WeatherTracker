package com.example.weathertracker.api.network

import com.example.weathertracker.api.model.WeatherData
import com.example.weathertracker.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json")
    suspend fun getCityWeather(@Query("key") apiKey: String = Constants.APP_KEY,
                               @Query("q") cityName: String,
                               @Query("aqi") aqi: String = "no"): WeatherData
}