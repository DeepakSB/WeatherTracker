package com.example.weathertracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weathertracker.ui.compose.WeatherScreen
import com.example.weathertracker.ui.theme.WeatherTrackerTheme
import com.example.weathertracker.utils.Constants


@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()

) {

    WeatherTrackerTheme {
        NavHost(
            navController = navController,
            startDestination = Constants.WEATHER_SCREEN
        ) {
            composable(route = Constants.WEATHER_SCREEN) {
                WeatherScreen(navController)
            }
        }
    }
}