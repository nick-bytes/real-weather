package com.example.realweather.repository.network

import com.example.realweather.model.Forecast
import com.example.realweather.model.ForecastResponse
import com.example.realweather.model.TodayForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetWeatherDataService {
    @GET("weather")
    fun retrieveTodayForecast(@Query("lat") latitude: Int, @Query("long") longitude: Int, @Query("APPID") apiKey: String?): Call<TodayForecastResponse?>?

    @GET("weather")
    fun retrieveTodayForecast(@Query("zip") zip: Int, @Query("APPID") apiKey: String?): Call<TodayForecastResponse?>?

    @GET("forecast")
    fun retrieveForecast(@Query("lat") latitude: Int, @Query("long") longitude: Int, @Query("APPID") apiKey: String?): Call<List<Forecast?>?>?

    @GET("forecast")
    fun retrieveForecast(@Query("zip") zip: Int, @Query("APPID") apiKey: String?): Call<ForecastResponse?>?
}
