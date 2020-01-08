package com.example.realweather.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "todayForecast")
public class TodayForecast {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("name")
    private String city;
    @ColumnInfo
    @TypeConverters(DataTypeConverter.class)
    private Weather weather;
    @ColumnInfo
    @TypeConverters(DataTypeConverter.class)
    private Main main;

    @SuppressWarnings({"unused", "required by Retrofit"})
    public TodayForecast() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

}



