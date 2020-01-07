package com.example.realweather.repository;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.realweather.repository.database.WeatherDatabaseClient;
import com.example.realweather.repository.service.RealWeatherSyncIntentService;

public class WeatherRepository implements WeatherDatabaseClient, RealWeatherSyncUtil {

    public static final WeatherRepository INSTANCE = new WeatherRepository();

    private WeatherRepository() {
    }

    public boolean hasBeenOnBoarded(Context context) {
        return false;
    }

    public void markAsOnboarded(Context context) {

    }

//    public static void startImmediateSync(@NonNull final Context context) {
//        Intent intentToSyncImmediately = new Intent(context, RealWeatherSyncIntentService.class);
//        context.startService(intentToSyncImmediately);
//    }


}
