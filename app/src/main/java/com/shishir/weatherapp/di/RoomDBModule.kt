package com.shishir.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.shishir.weatherapp.data.cache.WeatherDatabase
import com.shishir.weatherapp.data.cache.room.RoomConstants
import com.shishir.weatherapp.data.cache.room.dao.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room
            .databaseBuilder(context, WeatherDatabase::class.java, RoomConstants.WEATHER_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(weatherDatabase: WeatherDatabase): CityDao {
        return weatherDatabase.getCityWeatherDao()
    }
}