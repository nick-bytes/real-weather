package com.example.realweather.repository.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.realweather.model.Forecast;
import com.example.realweather.model.Weather;

import java.util.List;

/**
 * API for interacting with room database
 * <p>
 * Reference: https://developer.android.com/training/data-storage/room/accessing-data
 */

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeather(Weather weather);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForecasts(List<Forecast> forecasts);

    @Query("SELECT * FROM forecast")
    List<Forecast> loadForecasts();

    @Query("SELECT * FROM weather")
    Weather loadWeather();

}
