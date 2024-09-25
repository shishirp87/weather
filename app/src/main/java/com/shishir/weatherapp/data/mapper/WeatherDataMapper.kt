package com.shishir.weatherapp.data.mapper

import com.shishir.weatherapp.data.cache.room.entity.RMCityEntity
import com.shishir.weatherapp.data.cache.room.entity.RMCityForecastEntity
import com.shishir.weatherapp.data.network.response.WeatherResponseEntity
import com.shishir.weatherapp.data.network.response.ForecastdayResponseEntity
import com.shishir.weatherapp.model.ForecastModel
import com.shishir.weatherapp.model.WeatherModel
import java.util.Date

fun WeatherResponseEntity.toWeatherModel(): WeatherModel = WeatherModel(
    temperature = currentWeatherResponseEntity.tempC.toInt(),
    date = forecast.forecastday[0].date,
    wind = currentWeatherResponseEntity.windKph.toInt(),
    humidity = currentWeatherResponseEntity.humidity,
    feelsLike = currentWeatherResponseEntity.feelslikeC.toInt(),
    condition = currentWeatherResponseEntity.conditionResponseEntity.text,
    name = locationResponseEntity.name,
    forecasts = forecast.forecastday.map { networkForecastday ->
        networkForecastday.toWeatherForecastModel()
    }
)

fun ForecastdayResponseEntity.toWeatherForecastModel(): ForecastModel = ForecastModel(
    date = date,
    maxTemp = dayResponseEntity.maxtempC.toInt().toString(),
    minTemp = dayResponseEntity.mintempC.toInt().toString(),
    condition = dayResponseEntity.conditionResponseEntity.text
)

fun WeatherResponseEntity.toWeatherCacheEntity(): RMCityEntity = RMCityEntity(
    temperature = currentWeatherResponseEntity.tempC.toInt(),
    date = forecast.forecastday[0].date,
    wind = currentWeatherResponseEntity.windKph.toInt(),
    humidity = currentWeatherResponseEntity.humidity,
    feelsLike = currentWeatherResponseEntity.feelslikeC.toInt(),
    condition = currentWeatherResponseEntity.conditionResponseEntity.text,
    name = locationResponseEntity.name,
    forecastList = forecast.forecastday.map { networkForecastday ->
        networkForecastday.toForecastCacheEntity()
    },
    cacheTime = Date().time
)

fun ForecastdayResponseEntity.toForecastCacheEntity(): RMCityForecastEntity = RMCityForecastEntity(
    date = date,
    maxTemp = dayResponseEntity.maxtempC.toInt().toString(),
    minTemp = dayResponseEntity.mintempC.toInt().toString(),
    condition = dayResponseEntity.conditionResponseEntity.text
)


fun RMCityEntity.toWeatherModel(): WeatherModel = WeatherModel(
    temperature = temperature,
    date = date,
    wind = wind,
    humidity = humidity,
    feelsLike = feelsLike,
    condition = condition,
    name = name,
    forecasts = forecastList.map { networkForecastday ->
        networkForecastday.toWeatherForecastModel()
    }
)

fun RMCityForecastEntity.toWeatherForecastModel(): ForecastModel = ForecastModel(
    date = date,
    maxTemp = maxTemp,
    minTemp = minTemp,
    condition = condition
)