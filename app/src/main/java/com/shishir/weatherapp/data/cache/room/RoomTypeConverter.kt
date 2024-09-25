package com.shishir.weatherapp.data.cache.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shishir.weatherapp.data.cache.room.entity.RMCityForecastEntity

class RoomTypeConverter {

    @TypeConverter
    fun fromForecastList(value: List<RMCityForecastEntity>): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toForecastList(value: String): List<RMCityForecastEntity> {
        val listType = object : TypeToken<List<RMCityForecastEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }
}