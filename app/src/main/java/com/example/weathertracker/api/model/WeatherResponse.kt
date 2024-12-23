package com.example.weathertracker.api.model

data class WeatherData(val location: WeatherLocation,
                       val current: CurrentWeather)

data class WeatherLocation(val name: String, val region: String,
                           val country: String, val lat: Double,
                           val lon: Double, val tz_Id: String,
                           val localtime_epoch: Long, val localtime: String)

data class CurrentWeather(val last_updated_epoch: Double, val last_updated: String,
                          val temp_c: Double, val temp_f: Double,
                          val is_day: Int, val condition: WeatherCondition,
                          val wind_mph: Float, val wind_kph: Float, val wind_degree: Int,
                          val wind_dir: String, val pressure_mb: Float, val pressure_in: Float,
                          val precip_mm: Float, val precip_in: Float, val humidity: Int,
                          val cloud: Int, val feelslike_c: Float, val feelslike_f: Float,
                          val windchill_c: Float, val windchill_f: Float, val heatindex_c: Float,
                          val heatindex_f: Float, val dewpoint_c: Float, val dewpoint_f: Float,
                          val vis_km: Float, val vis_miles: Float, val uv: Float,
                          val gust_mph: Float, val gust_kph: Float)

data class WeatherCondition(val text: String, val icon: String, val code: Int)