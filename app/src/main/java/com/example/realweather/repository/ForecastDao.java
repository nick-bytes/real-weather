package com.example.realweather.repository;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.realweather.model.Forecast;

import java.util.List;

/**
 * API for interacting with room database
 * <p>
 * Reference: https://developer.android.com/training/data-storage/room/accessing-data
 */

@Dao
public interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForecast(List<Forecast> forecasts);

    @Query("SELECT * FROM forecast")
    LiveData<List<Forecast>> loadForecasts();

}
