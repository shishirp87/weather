package com.shishir.weatherapp.ui.dashboard

import com.shishir.weatherapp.model.WeatherModel

data class DashboardUiState (
    val weatherModel: WeatherModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)