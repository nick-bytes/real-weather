package com.example.realweather.view

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.realweather.repository.ForecastWorker
import com.example.realweather.repository.REAL_WEATHER_SYNC
import com.example.realweather.repository.SYNC_INTERVAL_HOURS
import com.example.realweather.repository.TodayForecastWorker
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ActivityLifecycleObserver : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        getWeatherForecast(WorkManager.getInstance(owner as Context))
    }

    private fun getWeatherForecast(workManager: WorkManager) {
        val todayForecastRequest =
            PeriodicWorkRequestBuilder<TodayForecastWorker>(SYNC_INTERVAL_HOURS, TimeUnit.HOURS)
                .addTag(REAL_WEATHER_SYNC)
                .build()

        val forecastRequest = OneTimeWorkRequestBuilder<ForecastWorker>()
            .addTag(REAL_WEATHER_SYNC)
            .build()

        workManager.enqueue(todayForecastRequest)
            .result
            .addListener(
                { workManager.beginWith(forecastRequest).enqueue() },
                Executors.newSingleThreadExecutor()
            )

    }
}
