package com.shishir.weatherapp.data.cache

import com.shishir.weatherapp.data.cache.room.dao.CityDao
import com.shishir.weatherapp.data.cache.room.entity.RMCityEntity
import javax.inject.Inject

class WeatherCacheImpl @Inject constructor(
    private val cityDao: CityDao
) : WeatherCache{
    override suspend fun insertCityWeatherDetails(cityDetails: RMCityEntity) {
        cityDao.insertCityWeatherDetails(cityDetails)
    }

    override suspend fun getCityWeatherDetails(cityName: String): RMCityEntity? {
        return cityDao.geCityWeatherDetails(cityName)
    }
}