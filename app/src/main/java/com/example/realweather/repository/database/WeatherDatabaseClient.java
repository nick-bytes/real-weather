package com.example.realweather.repository.database;

public interface WeatherDatabaseClient {

    default WeatherDao getWeatherDao() {
        return WeatherDatabaseValueHolder.INSTANCE.getValue().getWeatherDao();
    }
}
