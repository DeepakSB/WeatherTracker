package com.example.weathertracker.api.network

sealed interface ResultState {
    data class Success(val data: Any) : ResultState
    data class Error(val message: String) : ResultState
    data object Loading : ResultState
}