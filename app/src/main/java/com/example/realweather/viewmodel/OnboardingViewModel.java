package com.example.realweather.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.realweather.repository.PreferencesClient;
import com.example.realweather.repository.WeatherRepository;

public class OnboardingViewModel extends ViewModel implements PreferencesClient {

    private WeatherRepository repository = WeatherRepository.INSTANCE;

    public OnboardingViewModel() {

    }

    public boolean hasBeenOnboarded(Context context) {
        return repository.hasBeenOnBoarded(context);
    }

    public void markAsOnboarded(Context context) {
        repository.markAsOnboarded(context);
    }
}
