package com.example.realweather.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.realweather.model.Forecast;
import com.example.realweather.model.TodayForecast;
import com.example.realweather.repository.WeatherRepository;

import java.util.List;

public class SettingsViewModel extends ViewModel {


    private WeatherRepository repository = WeatherRepository.INSTANCE;

    public SettingsViewModel() {
    }

    public void updateLocationPreference(Context context) {
        repository.updateLocation(context);
    }

    public void updateUnitPreference(Context context) {
        repository.updateUnitPreference(context);
    }
}
