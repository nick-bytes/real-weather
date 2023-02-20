package com.example.realweather.repository

import android.content.Context
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.realweather.BuildConfig
import com.example.realweather.model.Forecast
import com.example.realweather.model.ForecastResponse
import com.example.realweather.model.TodayForecast
import com.example.realweather.model.TodayForecastResponse
import com.example.realweather.repository.database.WeatherDatabaseClient
import com.example.realweather.repository.network.GetWeatherDataConsumer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.Callable
import java.util.concurrent.Executors

/**
 * Object that provides weather data
 *
 * @author Nick Emerson
 */
class WeatherRepository private constructor() : WeatherDatabaseClient, GetWeatherDataConsumer,
    AsyncExecutor, PreferencesClient {
    private val forecastLiveData = MutableLiveData<List<Forecast>?>()
    private val todayForecastLiveData = MutableLiveData<TodayForecast?>()
    private fun considerFetchingForecasts(
        forecastList: List<Forecast>,
        zip: Int
    ): LiveData<List<Forecast>?> {
        return if (forecastList.isEmpty()) fetchForecast(zip) else toLiveData(forecastList)
    }

    private fun createFetchForecastCallable(zip: Int): Callable<Void> {
        return Callable {
            service.retrieveForecast(zip, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                ?.enqueue(object : Callback<ForecastResponse?> {
                    override fun onFailure(call: Call<ForecastResponse?>, throwable: Throwable) {
                        forecastLiveData.value = null
                        Timber.e(throwable)
                    }

                    override fun onResponse(
                        call: Call<ForecastResponse?>,
                        response: Response<ForecastResponse?>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.list?.let {
                                executeSingleThreadAsync(createStoreForecastAsyncCallable(it))
                            }

                        }
                    }
                })
            AsyncExecutor.NOTHING
        }
    }

    private fun createFetchWeatherCallable(zip: Int): Callable<Void> {
        return Callable {
            service.retrieveTodayForecast(zip, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                ?.enqueue(object : Callback<TodayForecastResponse?> {
                    override fun onFailure(
                        call: Call<TodayForecastResponse?>,
                        throwable: Throwable
                    ) {
                        todayForecastLiveData.value = null
                        Timber.e(throwable)
                    }

                    override fun onResponse(
                        call: Call<TodayForecastResponse?>,
                        response: Response<TodayForecastResponse?>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                val result = transformResponse(it)
                                executeSingleThreadAsync(
                                    createStoreTodayForecastAsyncCallable(
                                        result
                                    )
                                )
                            }
                        }
                    }
                })
            AsyncExecutor.NOTHING
        }
    }

    private fun createStoreForecastAsyncCallable(results: List<Forecast>): Collection<Callable<Void>> {
        return setOf(Callable {
            forecastDao.deleteForecastEntries()
            forecastDao.insertForecast(results)
            AsyncExecutor.NOTHING
        })
    }

    private fun createStoreTodayForecastAsyncCallable(result: TodayForecast): Collection<Callable<Void>> {
        return setOf(Callable {
            todayForecastDao.deleteTodayForecastEntry()
            todayForecastDao.insertTodayForecast(result)
            AsyncExecutor.NOTHING
        })
    }

    private fun fetchForecast(zip: Int): LiveData<List<Forecast>?> {
        service.retrieveForecast(zip, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
            ?.enqueue(object : Callback<ForecastResponse?> {
                override fun onFailure(call: Call<ForecastResponse?>, throwable: Throwable) {
                    forecastLiveData.value = null
                    Timber.e(throwable)
                }

                override fun onResponse(
                    call: Call<ForecastResponse?>,
                    response: Response<ForecastResponse?>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val results = response.body()!!.list
                        executeSingleThreadAsync(createStoreForecastAsyncCallable(results))
                    }
                }
            })
        return forecastLiveData
    }

    private fun fetchTodayForecast(zip: Int): LiveData<TodayForecast?> {
        service.retrieveTodayForecast(zip, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
            ?.enqueue(object : Callback<TodayForecastResponse?> {
                override fun onFailure(call: Call<TodayForecastResponse?>, throwable: Throwable) {
                    forecastLiveData.value = null
                    Timber.e(throwable)
                }

                override fun onResponse(
                    call: Call<TodayForecastResponse?>,
                    response: Response<TodayForecastResponse?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val result = transformResponse(it)
                            executeSingleThreadAsync(createStoreTodayForecastAsyncCallable(result))
                        }


                    }
                }
            })
        return todayForecastLiveData
    }

    private fun transformResponse(response: TodayForecastResponse): TodayForecast {
        return TodayForecast().apply {
            response.let {
                city = response.name
                weather = response.weather[0]
                main = response.main
            }
        }
    }

    fun initializeWeatherData(zip: Int) {
        executeAsync(
            listOf(createFetchForecastCallable(zip), createFetchWeatherCallable(zip)),
            Executors.newFixedThreadPool(3)
        )
    }

    fun loadForecasts(zip: Int): LiveData<List<Forecast>?> {
        return Transformations.switchMap(
            forecastDao.loadForecasts()
        ) { forecastList: List<Forecast> -> considerFetchingForecasts(forecastList, zip) }
    }

    val forecast: LiveData<List<Forecast>>
        get() = forecastDao.loadForecasts()
    val todayForecast: LiveData<TodayForecast?>
        get() = todayForecastDao.loadTodayForecast()

    fun loadTodayForecast(zip: Int): LiveData<TodayForecast?> {
        return Transformations.switchMap(
            todayForecastDao.loadTodayForecast(),
            Function { todayForecast: TodayForecast? ->
                considerFetchingTodayForecast(
                    todayForecast,
                    zip
                )
            })
    }

    private fun considerFetchingTodayForecast(
        todayForecast: TodayForecast?,
        zip: Int
    ): LiveData<TodayForecast?> {
        return todayForecast?.let { toLiveData(it) } ?: fetchTodayForecast(zip)
    }

    private fun toLiveData(forecastList: List<Forecast>): LiveData<List<Forecast>?> {
        forecastLiveData.value = forecastList
        return forecastLiveData
    }

    private fun toLiveData(todayForecast: TodayForecast): LiveData<TodayForecast?> {
        todayForecastLiveData.value = todayForecast
        return todayForecastLiveData
    }

    fun updateLocation(context: Context?) {
        resetLocationCoordinates(context)
        executeAsync(
            listOf(
                createFetchForecastCallable(getUserZipPreference(context)),
                createFetchWeatherCallable(getUserZipPreference(context))
            ),
            Executors.newFixedThreadPool(3)
        )
    }

    companion object {
        @JvmField
        val INSTANCE = WeatherRepository()
    }
}
