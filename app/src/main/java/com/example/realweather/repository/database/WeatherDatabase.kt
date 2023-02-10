package com.example.realweather.repository.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.realweather.model.Forecast;
import com.example.realweather.model.TodayForecast;
import com.example.realweather.repository.ForecastDao;

@Database(entities = {TodayForecast.class, Forecast.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract TodayForecastDao getTodayForecastDao();


    public abstract ForecastDao getForecastDao();
}