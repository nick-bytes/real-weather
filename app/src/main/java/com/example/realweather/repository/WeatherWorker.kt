package com.example.realweather.repository

import android.content.Context
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.realweather.BuildConfig
import com.example.realweather.model.Forecast
import com.example.realweather.model.ForecastResponse
import com.example.realweather.model.TodayForecast
import com.example.realweather.model.TodayForecastResponse
import com.example.realweather.repository.database.WeatherDatabaseClient
import com.example.realweather.repository.network.GetWeatherDataConsumer
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.Callable

class TodayForecastWorker(appContext: Context, workerParams: WorkerParameters) :
    ListenableWorker(appContext, workerParams), PreferencesClient, GetWeatherDataConsumer,
    AsyncExecutor, WeatherDatabaseClient {

    override fun startWork(): ListenableFuture<Result> {
        fun transformResponse(response: TodayForecastResponse?): TodayForecast {
            val todayForecast = TodayForecast()
            todayForecast.city = response?.name
            todayForecast.weather = response?.weather?.get(0)
            todayForecast.main = response?.main
            return todayForecast
        }

        return CallbackToFutureAdapter.getFuture {
            CoroutineScope(Dispatchers.IO).launch {
                service
                    .retrieveTodayForecast(
                        getUserZipPreference(applicationContext),
                        BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )
                    ?.enqueue(object : Callback<TodayForecastResponse?> {
                        override fun onFailure(
                            call: Call<TodayForecastResponse?>,
                            throwable: Throwable
                        ) {
                            Timber.e(throwable)
                            it.setException(throwable)
                        }

                        override fun onResponse(
                            call: Call<TodayForecastResponse?>,
                            response: Response<TodayForecastResponse?>
                        ) {
                            var result = Result.failure()
                            if (response.isSuccessful) {
                                response.body()?.let {
                                    val todayForecast: TodayForecast =
                                        transformResponse(response.body())
                                    executeSingleThreadAsync(setOf(Callable {
                                        todayForecastDao.deleteTodayForecastEntry()
                                        todayForecastDao.insertTodayForecast(todayForecast)
                                        AsyncExecutor.NOTHING
                                    }))
                                    result = Result.success()
                                }
                                it.set(result)
                            }
                        }
                    })
            }
        }
    }
}

class ForecastWorker(appContext: Context, workerParams: WorkerParameters) :
    ListenableWorker(appContext, workerParams), PreferencesClient, GetWeatherDataConsumer,
    AsyncExecutor, WeatherDatabaseClient {

    override fun startWork(): ListenableFuture<Result> {
        return CallbackToFutureAdapter.getFuture { completer ->
            CoroutineScope(Dispatchers.IO).launch {
                service
                    .retrieveForecast(
                        getUserZipPreference(applicationContext),
                        BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )?.enqueue(object : Callback<ForecastResponse?> {
                        override fun onFailure(
                            call: Call<ForecastResponse?>,
                            throwable: Throwable
                        ) {
                            Timber.e(throwable)
                            completer.setException(throwable)
                        }

                        override fun onResponse(
                            call: Call<ForecastResponse?>,
                            response: Response<ForecastResponse?>
                        ) {
                            var result = Result.failure()
                            if (response.isSuccessful) {
                                response.body()?.list?.let {
                                    val results: List<Forecast> = it
                                    if (it.isNotEmpty()) {
                                        executeSingleThreadAsync(setOf(Callable {
                                            forecastDao.deleteForecastEntries()
                                            forecastDao.insertForecast(results)
                                            AsyncExecutor.NOTHING
                                        }))
                                        result = Result.success()
                                    }

                                }

                            }
                            completer.set(result)
                        }
                    })
            }
        }
    }


}

const val REAL_WEATHER_SYNC = "REAL_WEATHER_SYNC"
const val SYNC_INTERVAL_HOURS = 3L
