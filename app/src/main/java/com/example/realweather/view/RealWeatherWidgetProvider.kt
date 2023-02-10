package com.example.realweather.view

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.example.realweather.R
import com.example.realweather.model.DisplayValueConverter
import com.example.realweather.model.TodayForecast
import com.example.realweather.repository.AsyncExecutor
import com.example.realweather.repository.PreferencesClient
import com.example.realweather.repository.database.WeatherDatabaseClient

/**
 * Implementation of App Widget functionality.
 */
class RealWeatherWidgetProvider : AppWidgetProvider(), PreferencesClient, WeatherDatabaseClient, AsyncExecutor {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray?) {
        appWidgetIds?.let {
            for (appWidgetId in it) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }

    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            intent.extras?.let {
                onUpdate(context, AppWidgetManager.getInstance(context),
                        it.get(AppWidgetManager.EXTRA_APPWIDGET_IDS) as? IntArray)
            }

        }
    }

    companion object {
        private val INSTANCE = RealWeatherWidgetProvider()
        private var OBSERVER: Observer<TodayForecast>? = null
        private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                    appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.widget_layout)
            OBSERVER = createTodayForecastObserver(context, appWidgetManager, appWidgetId, views)
            INSTANCE.todayForecastDao.loadTodayForecast()?.observeForever(OBSERVER)
        }

        private fun createTodayForecastObserver(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, views: RemoteViews): Observer<TodayForecast> {
            return Observer { weather: TodayForecast? ->
                if (weather != null) {
                    updateValues(views, weather, context)
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            }
        }

        private fun updateValues(views: RemoteViews, todayForecast: TodayForecast, context: Context) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val converter = DisplayValueConverter(sharedPreferences.getBoolean(context.getString(R.string.pref_units_key), false))
            views.setTextViewText(R.id.temperature, converter.formatTemperature(todayForecast.main.temp))
        }
    }
}
