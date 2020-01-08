package com.example.realweather.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.realweather.repository.WeatherRepository;

public class OnboardingViewModel extends ViewModel {

    private WeatherRepository repository = WeatherRepository.INSTANCE;

    public boolean hasBeenOnboarded(Context context) {
        return repository.hasBeenOnboarded(context);
    }

    public void markAsOnboarded(Context context) {
        repository.markAsOnboarded(context);
    }

    public void handleOnboarding(Context context, int zip) {
        repository.saveUserZip(context, zip);
        repository.markAsOnboarded(context);
    }
}
