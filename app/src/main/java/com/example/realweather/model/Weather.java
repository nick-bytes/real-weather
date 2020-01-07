package com.example.realweather.model;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "weather")
public class Weather {

    @SerializedName("name")
    private String city;
    @SerializedName("weather")
    private WeatherSummary weather;
    @SerializedName("main")
    private Main main;

    @SuppressWarnings({"unused", "required by Retrofit"})
    public Weather() {
    }

}



