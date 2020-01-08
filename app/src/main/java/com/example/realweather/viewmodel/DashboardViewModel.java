package com.example.realweather.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.realweather.model.Forecast;
import com.example.realweather.model.TodayForecast;
import com.example.realweather.repository.WeatherRepository;

import java.util.List;

public class DashboardViewModel extends ViewModel {

	private final int zip;
	private LiveData<List<Forecast>> forecastList;
	private WeatherRepository repository = WeatherRepository.INSTANCE;

	public DashboardViewModel(int zip) {
		this.zip = zip;
	}

	public LiveData<List<Forecast>> getForecast() {
		if (forecastList == null) {
			forecastList = repository.loadForecasts(zip);
		}
		return forecastList;
    }

	public LiveData<TodayForecast> getTodayForecast() {
		return repository.loadTodayForecast(zip);
    }


}
