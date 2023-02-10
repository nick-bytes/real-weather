package com.example.realweather.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.realweather.model.Forecast

/**
 * API for interacting with room database
 *
 *
 * Reference: https://developer.android.com/training/data-storage/room/accessing-data
 */
@Dao
interface ForecastDao {
    @Query("DELETE FROM forecast")
    fun deleteForecastEntries()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(forecasts: List<Forecast>)

    @Query("SELECT * FROM forecast")
    fun loadForecasts(): LiveData<List<Forecast>>
}
