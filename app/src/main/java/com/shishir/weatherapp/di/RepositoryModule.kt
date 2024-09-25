package com.shishir.weatherapp.di

import com.shishir.weatherapp.data.cache.WeatherCache
import com.shishir.weatherapp.data.cache.WeatherCacheImpl
import com.shishir.weatherapp.data.cache.room.dao.CityDao
import com.shishir.weatherapp.data.network.WeatherApi
import com.shishir.weatherapp.data.repository.WeatherRepositoryImpl
import com.shishir.weatherapp.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherCache(cityDao: CityDao): WeatherCache{
        return WeatherCacheImpl(cityDao)
    }

    @Singleton
    @Provides
    fun provideRepository(weatherApi: WeatherApi, weatherCache: WeatherCache): WeatherRepository =
        WeatherRepositoryImpl(weatherApi,weatherCache)
}