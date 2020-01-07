package com.example.realweather.repository.service;

import android.app.IntentService;
import android.content.Intent;

import com.example.realweather.repository.network.WeatherSyncTask;

public class RealWeatherSyncIntentService extends IntentService {

    public RealWeatherSyncIntentService() {
        super("RealWeatherSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        WeatherSyncTask.syncWeather(this);
    }
}
