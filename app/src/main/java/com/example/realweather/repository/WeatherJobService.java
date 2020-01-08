package com.example.realweather.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.realweather.model.Forecast;
import com.example.realweather.model.TodayForecast;
import com.example.realweather.repository.database.WeatherDatabaseClient;
import com.example.realweather.repository.network.GetWeatherDataConsumer;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.realweather.BuildConfig.OPEN_WEATHER_MAP_API_KEY;
import static com.firebase.jobdispatcher.Constraint.ON_ANY_NETWORK;

public class WeatherJobService extends JobService implements AsyncExecutor {

    static String REAL_WEATHER_SYNC = "REAL_WEATHER_SYNC";
    static int SYNC_INTERVAL_HOURS = 3;
    static int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    static int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
    private FetchForecastTask forecastTask;
    private FetchTodayForecastTask todayForecastTask;

    public static void scheduleJob(@NonNull final Context context) {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job weatherUpdateJob = dispatcher.newJobBuilder()
                .setService(WeatherJobService.class)
                .setTag(REAL_WEATHER_SYNC)
                .setConstraints(ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(weatherUpdateJob);
    }

    @Override
    public boolean onStartJob(@NonNull JobParameters job) {
        forecastTask = new FetchForecastTask() {
            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(job, false);
            }
        };

        todayForecastTask = new FetchTodayForecastTask() {
            @Override
            protected void onPostExecute(Void aVoid) {
                forecastTask.execute();
            }
        };

        todayForecastTask.execute();
        return true;

    }

    @Override
    public boolean onStopJob(@NonNull JobParameters job) {
        return false;
    }

    public static class FetchTodayForecastTask extends AsyncTask<Context, Void, Void> implements GetWeatherDataConsumer,
            PreferencesClient, AsyncExecutor, WeatherDatabaseClient {
        @Override
        protected Void doInBackground(Context... contexts) {
            getService().retrieveTodayForecast(getUserZipPreference(contexts[0]), OPEN_WEATHER_MAP_API_KEY).enqueue(new Callback<TodayForecast>() {
                @Override
                public void onFailure(@NonNull Call<TodayForecast> call, @NonNull Throwable throwable) {
                    // TODO: 1/8/2020 fetchForecasttask.execute
                }

                @Override
                public void onResponse(@NonNull Call<TodayForecast> call, @NonNull Response<TodayForecast> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        TodayForecast result = response.body();
                        executeSingleThreadAsync((Collections.singleton(() -> {
                            getTodayForecastDao().insertTodayForecast(result);
                            return NOTHING;
                        })));
                    }
                }
            });

            return NOTHING;
        }


    }

    public static class FetchForecastTask extends AsyncTask<Context, Void, Void> implements GetWeatherDataConsumer,
            PreferencesClient, AsyncExecutor, WeatherDatabaseClient {
        @Override
        protected Void doInBackground(Context... contexts) {
            getService().retrieveForecast(getUserZipPreference(contexts[0]), OPEN_WEATHER_MAP_API_KEY).enqueue(new Callback<List<Forecast>>() {
                @Override
                public void onFailure(@NonNull Call<List<Forecast>> call, @NonNull Throwable throwable) {
                    // TODO: 1/8/2020 fetchForecasttask.execute
                }

                @Override
                public void onResponse(@NonNull Call<List<Forecast>> call, @NonNull Response<List<Forecast>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Forecast> results = response.body();
                        executeSingleThreadAsync((Collections.singleton(() -> {
                            getForecastDao().insertForecast(results);
                            return NOTHING;
                        })));
                    }
                }
            });

            return NOTHING;
        }
    }
}
