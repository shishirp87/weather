package com.shishir.weatherapp.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shishir.weatherapp.data.cache.room.RoomTypeConverter
import com.shishir.weatherapp.data.cache.room.dao.CityDao
import com.shishir.weatherapp.data.cache.room.entity.RMCityEntity


@TypeConverters(RoomTypeConverter::class)
@Database(
    entities = [RMCityEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getCityWeatherDao(): CityDao
}