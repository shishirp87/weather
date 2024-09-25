package com.shishir.weatherapp.data.repository

import com.shishir.weatherapp.data.cache.WeatherCache
import com.shishir.weatherapp.data.mapper.toWeatherCacheEntity
import com.shishir.weatherapp.data.mapper.toWeatherModel
import com.shishir.weatherapp.data.network.WeatherApi
import com.shishir.weatherapp.utils.Result
import com.shishir.weatherapp.model.WeatherModel
import com.shishir.weatherapp.utils.DateUtil.calculateMinutesDifference
import com.shishir.weatherapp.utils.MAX_CACHE_TIME_MINUTES
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherCache: WeatherCache,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : WeatherRepository {
    override fun getWeatherForecast(city: String): Flow<Result<WeatherModel>> = flow {
        emit(Result.Loading)
        try {
            val cachedCityWeatherDetails = weatherCache.getCityWeatherDetails(city)
            if (cachedCityWeatherDetails == null ||
                cachedCityWeatherDetails.cacheTime.calculateMinutesDifference() > MAX_CACHE_TIME_MINUTES)
            {
                val result = weatherApi.getWeatherForecast(city = city)
                weatherCache.insertCityWeatherDetails(result.toWeatherCacheEntity())
                emit(Result.Success(result.toWeatherModel()))
            } else {
                emit(Result.Success(cachedCityWeatherDetails.toWeatherModel()))
            }
        } catch (exception: HttpException) {
            emit(Result.Error(exception.message.orEmpty()))
        } catch (exception: IOException) {
            emit(Result.Error("Please check your network connection and try again!"))
        }
    }.flowOn(dispatcher)
}