package com.shishir.weatherapp.model


data class WeatherModel(
    val temperature: Int,
    val date: String,
    val wind: Int,
    val humidity: Int,
    val feelsLike: Int,
    val condition: String,
    val name: String,
    val forecasts: List<ForecastModel>
)