package com.example.realweather.repository.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.realweather.model.Weather;

@Database(entities = {Weather.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract WeatherDao getWeatherDao();
}