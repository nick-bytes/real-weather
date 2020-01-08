package com.example.realweather.repository.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.realweather.model.TodayForecast;

/**
 * API for interacting with room database
 * <p>
 * Reference: https://developer.android.com/training/data-storage/room/accessing-data
 */

@Dao
public interface TodayForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTodayForecast(TodayForecast todayForecast);

    @Query("SELECT * FROM todayForecast")
    LiveData<TodayForecast> loadTodayForecast();

}
