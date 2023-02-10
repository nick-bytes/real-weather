package com.example.realweather.view;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;

import com.example.realweather.R;
import com.example.realweather.model.DisplayValueConverter;
import com.example.realweather.model.TodayForecast;
import com.example.realweather.repository.AsyncExecutor;
import com.example.realweather.repository.PreferencesClient;
import com.example.realweather.repository.database.WeatherDatabaseClient;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static android.appwidget.AppWidgetManager.getInstance;

/**
 * Implementation of App Widget functionality.
 */
public class RealWeatherWidgetProvider extends AppWidgetProvider implements PreferencesClient, WeatherDatabaseClient, AsyncExecutor {


    public static final RealWeatherWidgetProvider INSTANCE = new RealWeatherWidgetProvider();
    public static Observer<TodayForecast> OBSERVER;

    private static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                        final int appWidgetId) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        OBSERVER = createTodayForecastObserver(context, appWidgetManager, appWidgetId, views);
        INSTANCE.getTodayForecastDao().loadTodayForecast().observeForever(OBSERVER);
    }

    @NotNull
    private static Observer<TodayForecast> createTodayForecastObserver(Context context, AppWidgetManager appWidgetManager, int appWidgetId, RemoteViews views) {
        return weather -> {
            if (weather != null) {
                updateValues(views, weather, context);
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        };
    }

    private static void updateValues(RemoteViews views, TodayForecast todayForecast, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        DisplayValueConverter converter = new DisplayValueConverter(sharedPreferences.getBoolean(context.getString(R.string.pref_units_key), false));
        views.setTextViewText(R.id.temperature, converter.formatTemperature(todayForecast.getMain().getTemp()));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (Objects.equals(intent.getAction(), ACTION_APPWIDGET_UPDATE)) {
            int[] ids = (int[]) Objects.requireNonNull(intent.getExtras()).get(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            onUpdate(context, getInstance(context), Objects.requireNonNull(ids));
        }
    }
}

