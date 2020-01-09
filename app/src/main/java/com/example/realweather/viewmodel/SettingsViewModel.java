package com.example.realweather.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.realweather.repository.WeatherRepository;

public class SettingsViewModel extends ViewModel {


    private WeatherRepository repository = WeatherRepository.INSTANCE;

    public SettingsViewModel() {
    }

    public void reinitializeWeatherData(int zip) {
        repository.initializeWeatherData(zip);
    }

}
