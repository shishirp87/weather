package com.shishir.weatherapp.model


data class ForecastModel(
    val date: String,
    val maxTemp: String,
    val minTemp: String,
    val condition: String
)