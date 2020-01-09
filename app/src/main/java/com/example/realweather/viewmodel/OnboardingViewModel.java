package com.example.realweather.viewmodel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import androidx.lifecycle.ViewModel;

import com.example.realweather.repository.WeatherRepository;

import java.util.Objects;

public class OnboardingViewModel extends ViewModel {

    private WeatherRepository repository = WeatherRepository.INSTANCE;

    public boolean hasBeenOnboarded(Context context) {
        return repository.hasBeenOnboarded(context);
    }

    public void handleOnboarding(Context context, String zip) {
        repository.saveUserZip(context, zip);
        repository.markAsOnboarded(context);
        repository.initializeWeatherData(Integer.valueOf(zip));
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = Objects.requireNonNull(connectivityManager).getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return (Objects.requireNonNull(networkCapabilities).hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
    }
}
