package com.example.realweather.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.realweather.model.Forecast;
import com.example.realweather.model.ForecastResponse;
import com.example.realweather.model.TodayForecast;
import com.example.realweather.model.TodayForecastResponse;
import com.example.realweather.repository.database.WeatherDatabaseClient;
import com.example.realweather.repository.network.GetWeatherDataConsumer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.example.realweather.BuildConfig.OPEN_WEATHER_MAP_API_KEY;

/**
 * Object that provides weather data
 *
 * @author Nick Emerson
 */
public class WeatherRepository implements WeatherDatabaseClient, GetWeatherDataConsumer,
		AsyncExecutor, PreferencesClient {

	public static final WeatherRepository INSTANCE = new WeatherRepository();
	private final MutableLiveData<List<Forecast>> forecastLiveData = new MutableLiveData<>();
	private final MutableLiveData<TodayForecast> todayForecastLiveData = new MutableLiveData<>();

	private WeatherRepository() {
	}

	private LiveData<List<Forecast>> considerFetchingForecasts(List<Forecast> forecastList, int zip) {
		return isNullOrEmpty(forecastList) ? fetchForecast(zip) : toLiveData(forecastList);
	}

	private <T> boolean isNullOrEmpty(List<T> list) {
		return list == null || list.isEmpty();
	}

	private Callable<Void> createFetchForecastCallable(int zip) {
		return () -> {
			getService().retrieveForecast(zip, OPEN_WEATHER_MAP_API_KEY).enqueue(new Callback<ForecastResponse>() {
				@Override
				public void onFailure(@NonNull Call<ForecastResponse> call, @NonNull Throwable throwable) {
					forecastLiveData.setValue(null);
					Timber.e(throwable);
				}

				@Override
				public void onResponse(@NonNull Call<ForecastResponse> call, @NonNull Response<ForecastResponse> response) {
					if (response.isSuccessful() && response.body() != null) {
						List<Forecast> results = response.body().getList();
						executeSingleThreadAsync(createStoreForecastAsyncCallable(results));
					}
				}
			});

			return NOTHING;
		};
	}

	private Callable<Void> createFetchWeatherCallable(int zip) {
		return () -> {
			getService().retrieveTodayForecast(zip, OPEN_WEATHER_MAP_API_KEY).enqueue(new Callback<TodayForecastResponse>() {
				@Override
				public void onFailure(@NonNull Call<TodayForecastResponse> call, @NonNull Throwable throwable) {
					todayForecastLiveData.setValue(null);
					Timber.e(throwable);
				}

				@Override
				public void onResponse(@NonNull Call<TodayForecastResponse> call, @NonNull Response<TodayForecastResponse> response) {
					if (response.isSuccessful() && response.body() != null) {
						TodayForecast result = transformResponse(response.body());
						executeSingleThreadAsync(createStoreTodayForecastAsyncCallable(result));
					}
				}
			});

			return NOTHING;

		};
	}

	private Collection<Callable<Void>> createStoreForecastAsyncCallable(List<Forecast> results) {
		return Collections.singleton(() -> {
			getForecastDao().deleteForecastEntries();
			getForecastDao().insertForecast(results);
			return NOTHING;
		});

	}

	private Collection<Callable<Void>> createStoreTodayForecastAsyncCallable(TodayForecast result) {
		return Collections.singleton(() -> {
			getTodayForecastDao().deleteTodayForecastEntry();
			getTodayForecastDao().insertTodayForecast(result);
			return NOTHING;
		});

	}

	private LiveData<List<Forecast>> fetchForecast(int zip) {
		getService().retrieveForecast(zip, OPEN_WEATHER_MAP_API_KEY).enqueue(new Callback<ForecastResponse>() {
			@Override
			public void onFailure(@NonNull Call<ForecastResponse> call, @NonNull Throwable throwable) {
				forecastLiveData.setValue(null);
				Timber.e(throwable);
			}

			@Override
			public void onResponse(@NonNull Call<ForecastResponse> call, @NonNull Response<ForecastResponse> response) {
				if (response.isSuccessful() && response.body() != null) {
					List<Forecast> results = response.body().getList();
					executeSingleThreadAsync(createStoreForecastAsyncCallable(results));
				}
			}
		});

		return forecastLiveData;
	}

	private LiveData<TodayForecast> fetchTodayForecast(int zip) {
		getService().retrieveTodayForecast(zip, OPEN_WEATHER_MAP_API_KEY).enqueue(new Callback<TodayForecastResponse>() {
			@Override
			public void onFailure(@NonNull Call<TodayForecastResponse> call, @NonNull Throwable throwable) {
				forecastLiveData.setValue(null);
				Timber.e(throwable);
			}

			@Override
			public void onResponse(@NonNull Call<TodayForecastResponse> call, @NonNull Response<TodayForecastResponse> response) {
				if (response.isSuccessful() && response.body() != null) {
					TodayForecast result = transformResponse(response.body());
					executeSingleThreadAsync(createStoreTodayForecastAsyncCallable(result));
				}
			}
		});

		return todayForecastLiveData;
	}

	private TodayForecast transformResponse(TodayForecastResponse response) {
		TodayForecast todayForecast = new TodayForecast();
		todayForecast.setCity(response.getName());
		todayForecast.setWeather(response.getWeather().get(0));
		todayForecast.setMain(response.getMain());
		return todayForecast;
	}

	public void initializeWeatherData(int zip) {
		executeAsync(Arrays.asList(createFetchForecastCallable(zip), createFetchWeatherCallable(zip)),
				Executors.newFixedThreadPool(3));
	}

	public LiveData<List<Forecast>> loadForecasts(int zip) {
		return Transformations.switchMap(getForecastDao().loadForecasts(),
				forecastList -> considerFetchingForecasts(forecastList, zip));
	}

	public LiveData<List<Forecast>> getForecast() {
		return getForecastDao().loadForecasts();
	}


	public LiveData<TodayForecast> getTodayForecast() {
		return getTodayForecastDao().loadTodayForecast();
	}


	public LiveData<TodayForecast> loadTodayForecast(int zip) {
		return Transformations.switchMap(getTodayForecastDao().loadTodayForecast(), todayForecast -> considerFetchingTodayForecast(todayForecast, zip));
	}

	private LiveData<TodayForecast> considerFetchingTodayForecast(TodayForecast todayForecast, int zip) {
		return todayForecast == null ? fetchTodayForecast(zip) : toLiveData(todayForecast);
	}

	private LiveData<List<Forecast>> toLiveData(List<Forecast> forecastList) {
		forecastLiveData.setValue(forecastList);
		return forecastLiveData;
	}

	private LiveData<TodayForecast> toLiveData(TodayForecast todayForecast) {
		todayForecastLiveData.setValue(todayForecast);
		return todayForecastLiveData;
	}


	public void updateLocation(Context context) {
		resetLocationCoordinates(context);
		executeAsync(Arrays.asList(createFetchForecastCallable(getUserZipPreference(context)), createFetchWeatherCallable(getUserZipPreference(context))),
				Executors.newFixedThreadPool(3));
	}

}
