package com.example.realweather.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.realweather.model.Forecast;
import com.example.realweather.repository.WeatherRepository;

import java.util.List;

public class DashboardViewModel extends ViewModel {


    private WeatherRepository repository = WeatherRepository.INSTANCE;

    public LiveData<List<Forecast>> getForecast() {
        return null;
    }

    public LiveData<List<Forecast>> getWeather() {
        return null;
    }

    public void scheduleJob(@NonNull Context context) {
        repository.scheduleJob(context);
    }

}
