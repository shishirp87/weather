package com.shishir.weatherapp.data.cache

import com.shishir.weatherapp.data.cache.room.entity.RMCityEntity

interface WeatherCache {
    suspend fun insertCityWeatherDetails(cityDetails: RMCityEntity)
    suspend fun getCityWeatherDetails(cityName: String):RMCityEntity?
}