package com.shishir.weatherapp.data.repository

import com.shishir.weatherapp.model.WeatherModel
import kotlinx.coroutines.flow.Flow
import com.shishir.weatherapp.utils.Result

interface WeatherRepository {
    fun getWeatherForecast(city: String): Flow<Result<WeatherModel>>
}