package com.example.realweather.repository.network;

import com.example.realweather.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetWeatherDataService {

    @GET("weather")
    Call<Weather> retrieveWeather(@Query("lat") int latitude, @Query("long") int longitude, @Query("api_key") String apiKey);

    @GET("weather")
    Call<Weather> retrieveWeather(@Query("zip") int zip, @Query("api_key") String apiKey);

    @GET("forecast")
    Call<ForecastResponse> retrieveForecast(@Query("lat") int latitude, @Query("long") int longitude, @Query("api_key") String apiKey);

    @GET("forecast")
    Call<ForecastResponse> retrieveForecast(@Query("zip") int zip, @Query("api_key") String apiKey);


}
