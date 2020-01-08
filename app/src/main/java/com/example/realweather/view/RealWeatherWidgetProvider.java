package com.example.realweather.view;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.realweather.R;
import com.example.realweather.model.TodayForecast;
import com.example.realweather.repository.AsyncExecutor;
import com.example.realweather.repository.PreferencesClient;
import com.example.realweather.repository.RealWeatherUtilConsumer;
import com.example.realweather.repository.database.WeatherDatabaseClient;

import java.util.Objects;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static android.appwidget.AppWidgetManager.getInstance;

/**
 * Implementation of App Widget functionality.
 */
public class RealWeatherWidgetProvider extends AppWidgetProvider implements PreferencesClient, WeatherDatabaseClient, RealWeatherUtilConsumer, AsyncExecutor {


    public static final RealWeatherWidgetProvider INSTANCE = new RealWeatherWidgetProvider();

    private static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                        final int appWidgetId) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        INSTANCE.getTodayForecastDao().loadTodayForecast().observeForever(weather -> {
            if (weather != null) {
                updateValues(views, weather, context);
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        });
    }

    private static void updateValues(RemoteViews views, TodayForecast todayForecast, Context context) {
        views.setTextViewText(R.id.location, INSTANCE.getSelectedPosition(context)[0]);
        views.setTextViewText(R.id.temperature, INSTANCE.formatTemperature(context, todayForecast.getMain().getTemp()));
        views.setImageViewResource(R.id.weatherIcon, INSTANCE.getIcon(todayForecast.getWeather().getDescription()));
        views.setTextViewText(R.id.maxTemp, INSTANCE.formatTemperature(context, todayForecast.getMain().getMaxTemp()));
        views.setTextViewText(R.id.minTemp, INSTANCE.formatTemperature(context, todayForecast.getMain().getMinTemp()));
        views.setTextViewText(R.id.pressure, INSTANCE.formatPressure(todayForecast.getMain().getPressure()));
        views.setTextViewText(R.id.humidity, INSTANCE.formatHumidity(todayForecast.getMain().getHumidity()));

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

        // TODO: 1/7/2020 stop observing?
    }

//	public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//		for (int appWidgetId : appWidgetIds) {
//			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
//			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_time_widget);
//			views.setTextViewText(R.id.widget_textview_title, INSTANCE.getWidgetTitle(context));
//			views.setRemoteAdapter(R.id.widget_listview_ingredients, RealWeatherRemoteViewsService.getIntent(context));
//			views.setPendingIntentTemplate(R.id.widget_listview_ingredients, pendingIntent);
//			views.setOnClickPendingIntent(R.id.widget_parent_layout, pendingIntent);
//			appWidgetManager.updateAppWidget(appWidgetId, views);
//		}
//	}
}

