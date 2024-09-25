package com.shishir.weatherapp.data.network.response

import com.google.gson.annotations.SerializedName

data class LocationResponseEntity(
    @SerializedName("name") val name: String
)

data class ConditionResponseEntity(
    @SerializedName("text") val text: String
)

data class ForecastdayResponseEntity(
    @SerializedName("date") val date: String,
    @SerializedName("day") val dayResponseEntity: DayResponseEntity
)

data class CurrentWeatherResponseEntity(
    @SerializedName("condition") val conditionResponseEntity: ConditionResponseEntity,
    @SerializedName("feelslike_c") val feelslikeC: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("wind_kph") val windKph: Double
)

data class ForecastResponseEntity(
    @SerializedName("forecastday") val forecastday: List<ForecastdayResponseEntity>
)

data class DayResponseEntity(
    @SerializedName("condition") val conditionResponseEntity: ConditionResponseEntity,
    @SerializedName("maxtemp_c") val maxtempC: Double,
    @SerializedName("mintemp_c") val mintempC: Double,
)

data class WeatherResponseEntity(
    @SerializedName("current") val currentWeatherResponseEntity: CurrentWeatherResponseEntity,
    @SerializedName("forecast") val forecast: ForecastResponseEntity,
    @SerializedName("location") val locationResponseEntity: LocationResponseEntity
)