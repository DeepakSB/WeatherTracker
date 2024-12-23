package com.example.weathertracker.api.data


import com.example.weathertracker.api.network.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val weatherTrackerRepository: WeatherTrackerRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.weatherapi.com/v1/"
    private val okHttp = OkHttpClient.Builder().build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttp)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val weatherTrackerRepository: WeatherTrackerRepository by lazy {
        WeatherServiceRepositoryImpl(retrofitService)
    }
}