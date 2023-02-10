package com.example.realweather.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.realweather.model.Forecast
import com.example.realweather.model.TodayForecast
import com.example.realweather.repository.ForecastDao

@Database(entities = [TodayForecast::class, Forecast::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val todayForecastDao: TodayForecastDao
    abstract val forecastDao: ForecastDao
}
