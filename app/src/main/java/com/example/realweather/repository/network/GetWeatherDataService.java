package com.example.realweather.repository.network;

import com.example.realweather.model.Forecast;
import com.example.realweather.model.ForecastResponse;
import com.example.realweather.model.TodayForecast;
import com.example.realweather.model.TodayForecastResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetWeatherDataService {

    @GET("weather")
    Call<TodayForecastResponse> retrieveTodayForecast(@Query("lat") int latitude, @Query("long") int longitude, @Query("APPID") String apiKey);

    @GET("weather")
    Call<TodayForecastResponse> retrieveTodayForecast(@Query("zip") int zip, @Query("APPID") String apiKey);

    @GET("forecast")
    Call<List<Forecast>> retrieveForecast(@Query("lat") int latitude, @Query("long") int longitude, @Query("APPID") String apiKey);

    @GET("forecast")
    Call<ForecastResponse> retrieveForecast(@Query("zip") int zip, @Query("APPID") String apiKey);


}
