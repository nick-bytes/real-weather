package com.example.realweather.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.realweather.model.Forecast;
import com.example.realweather.model.TodayForecast;
import com.example.realweather.repository.WeatherRepository;

import java.util.List;

public class DashboardViewModel extends ViewModel {

	private LiveData<List<Forecast>> forecastList;
	private LiveData<TodayForecast> todayForecast;
    private final WeatherRepository repository = WeatherRepository.INSTANCE;

	public DashboardViewModel() {

	}

	public LiveData<List<Forecast>> getForecast() {
		if (forecastList == null) {
			forecastList = repository.getForecast();
		}
		return forecastList;
    }

	public LiveData<TodayForecast> getTodayForecast() {
		if (todayForecast == null) {
			todayForecast = repository.getTodayForecast();
		}
		return todayForecast;
    }


}
