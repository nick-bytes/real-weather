package com.example.realweather

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.example.realweather.repository.database.WeatherDatabase
import com.example.realweather.repository.database.WeatherDatabaseValueHolder
import timber.log.Timber
import timber.log.Timber.DebugTree

class RealWeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        WeatherDatabaseValueHolder.INSTANCE.value = databaseBuilder(this, WeatherDatabase::class.java, "WeatherDatabase").build()
    }
}
