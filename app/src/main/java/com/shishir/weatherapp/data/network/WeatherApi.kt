package com.shishir.weatherapp.data.network

import com.shishir.weatherapp.BuildConfig
import com.shishir.weatherapp.data.network.response.WeatherResponseEntity
import com.shishir.weatherapp.utils.DEFAULT_WEATHER_DESTINATION
import com.shishir.weatherapp.utils.NUMBER_OF_DAYS
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") key: String = BuildConfig.WEATHERAPI_KEY,
        @Query("q") city: String = DEFAULT_WEATHER_DESTINATION,
        @Query("days") days: Int = NUMBER_OF_DAYS,
    ): WeatherResponseEntity
}