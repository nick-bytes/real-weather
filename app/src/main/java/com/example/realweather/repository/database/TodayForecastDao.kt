package com.example.realweather.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.realweather.model.TodayForecast

/**
 * API for interacting with room database
 *
 *
 * Reference: [...](https://developer.android.com/training/data-storage/room/accessing-data)
 */
@Dao
interface TodayForecastDao {
    @Query("DELETE FROM todayForecast")
    fun deleteTodayForecastEntry()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodayForecast(todayForecast: TodayForecast?)

    @Query("SELECT * FROM todayForecast")
    fun loadTodayForecast(): LiveData<TodayForecast?>?
}
