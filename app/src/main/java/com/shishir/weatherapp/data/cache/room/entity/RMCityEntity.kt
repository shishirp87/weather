package com.shishir.weatherapp.data.cache.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shishir.weatherapp.data.cache.room.RoomConstants

@Entity(tableName = RoomConstants.CITY_WEATHER_INFO_TABLE)
data class RMCityEntity(
    @PrimaryKey
    val name: String,
    val temperature: Int,
    val date: String,
    val wind: Int,
    val humidity: Int,
    val feelsLike: Int,
    val condition: String,
    val forecastList: List<RMCityForecastEntity>,
    val cacheTime: Long
)
