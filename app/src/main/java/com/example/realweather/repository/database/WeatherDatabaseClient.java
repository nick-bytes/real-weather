package com.example.realweather.repository.database;

import com.example.realweather.repository.ForecastDao;

public interface WeatherDatabaseClient {

    default TodayForecastDao getTodayForecastDao() {
        return WeatherDatabaseValueHolder.INSTANCE.getValue().getTodayForecastDao();
    }

    default ForecastDao getForecastDao() {
        return WeatherDatabaseValueHolder.INSTANCE.getValue().getForecastDao();
    }
}
