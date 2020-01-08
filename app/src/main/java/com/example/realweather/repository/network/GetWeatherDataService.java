package com.example.realweather.repository.network;

import com.example.realweather.model.Forecast;
import com.example.realweather.model.TodayForecast;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetWeatherDataService {

    @GET("weather")
    Call<TodayForecast> retrieveTodayForecast(@Query("lat") int latitude, @Query("long") int longitude, @Query("api_key") String apiKey);

    @GET("weather")
    Call<TodayForecast> retrieveTodayForecast(@Query("zip") int zip, @Query("api_key") String apiKey);

    @GET("forecast")
    Call<List<Forecast>> retrieveForecast(@Query("lat") int latitude, @Query("long") int longitude, @Query("api_key") String apiKey);

    @GET("forecast")
    Call<List<Forecast>> retrieveForecast(@Query("zip") int zip, @Query("api_key") String apiKey);


}
