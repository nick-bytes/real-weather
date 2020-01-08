package com.example.realweather.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.List;

public class DataTypeConverter {

    private static final Gson GSON = new Gson();

    @TypeConverter
    public static String forecastListToString(List<Forecast> forecastList) {
        return GSON.toJson(forecastList);
    }

    @TypeConverter
    public static String weatherToString(Weather weather) {
        return GSON.toJson(weather);
    }

    @TypeConverter
    public static List<Forecast> stringToIngredientList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        return GSON.fromJson(data, new TypeToken<List<Forecast>>() {
        }.getType());
    }

    @TypeConverter
    public static Weather stringToWeather(String data) {
        return GSON.fromJson(data, new TypeToken<Weather>() {
        }.getType());
    }

    @TypeConverter
    public static Main stringToMain(String data) {
        return GSON.fromJson(data, new TypeToken<Main>() {
        }.getType());
    }

    @TypeConverter
    public static String mainToString(Main main) {
        return GSON.toJson(main);
    }

}
