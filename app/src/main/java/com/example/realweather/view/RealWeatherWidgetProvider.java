package com.example.realweather.view;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.realweather.R;
import com.example.realweather.model.Forecast;
import com.example.realweather.repository.AsyncExecutor;
import com.example.realweather.repository.PreferencesClient;
import com.example.realweather.repository.RealWeatherUtilConsumer;
import com.example.realweather.repository.database.WeatherDatabaseClient;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static android.appwidget.AppWidgetManager.getInstance;
import static java.text.DateFormat.SHORT;
import static java.text.DateFormat.getDateInstance;

/**
 * Implementation of App Widget functionality.
 */
public class RealWeatherWidgetProvider extends AppWidgetProvider implements PreferencesClient, WeatherDatabaseClient, RealWeatherUtilConsumer, AsyncExecutor {

    public static final RealWeatherWidgetProvider INSTANCE = new RealWeatherWidgetProvider();

    private static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                        final int appWidgetId) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        INSTANCE.executeSingleThreadAsync(Collections.singleton(() -> {
            List<Forecast> forecastList = INSTANCE.getWeatherDao().loadForecasts();
            if (!forecastList.isEmpty()) {
                updateValues(views, forecastList, context);
                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }

            return NOTHING;
        }));
    }

    private static void updateValues(RemoteViews views, List<Forecast> forecasts, Context context) {
        views.setTextViewText(R.id.date, getDateInstance(SHORT).format(forecasts.get(0).getDate()));
        views.setTextViewText(R.id.location, INSTANCE.getSelectedPosition(context)[0]);
        views.setTextViewText(R.id.temperature, INSTANCE.formatTemperature(context, forecasts.get(0).getMain().getTemp()));
        views.setImageViewResource(R.id.weatherIcon, INSTANCE.getIcon(forecasts.get(0).getWeatherSummary().getDescription()));
        views.setTextViewText(R.id.maxTemp, INSTANCE.formatTemperature(context, forecasts.get(0).getMain().getMaxTemp()));
        views.setTextViewText(R.id.minTemp, INSTANCE.formatTemperature(context, forecasts.get(0).getMain().getMinTemp()));
        views.setTextViewText(R.id.pressure, INSTANCE.formatPressure(forecasts.get(0).getMain().getPressure()));
        views.setTextViewText(R.id.humidity, INSTANCE.formatHumidity(forecasts.get(0).getMain().getHumidity()));

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

