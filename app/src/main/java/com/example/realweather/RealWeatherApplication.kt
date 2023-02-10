package com.example.realweather;

import android.app.Application;

import androidx.room.Room;

import com.example.realweather.repository.database.WeatherDatabase;
import com.example.realweather.repository.database.WeatherDatabaseValueHolder;

import timber.log.Timber;

public class RealWeatherApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
			if (BuildConfig.DEBUG) {
				Timber.plant(new Timber.DebugTree());
			}
			WeatherDatabaseValueHolder.INSTANCE.setValue(Room.databaseBuilder(this, WeatherDatabase.class, "WeatherDatabase").build());
		}

}
