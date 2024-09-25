package com.shishir.weatherapp.data.cache.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shishir.weatherapp.data.cache.room.RoomConstants
import com.shishir.weatherapp.data.cache.room.entity.RMCityEntity

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCityWeatherDetails(rmCity: RMCityEntity)

    @Query("SELECT * FROM ${RoomConstants.CITY_WEATHER_INFO_TABLE} WHERE name=:cityName COLLATE NOCASE")
    fun geCityWeatherDetails(cityName: String): RMCityEntity?
}