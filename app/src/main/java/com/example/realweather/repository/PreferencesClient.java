package com.example.realweather.repository;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.realweather.model.UnitType;

import static com.example.realweather.repository.Constants.NO_LATITUDE;
import static com.example.realweather.repository.Constants.NO_LONGITUDE;
import static com.example.realweather.repository.Constants.NO_PLACE;
import static com.example.realweather.repository.Constants.PREFERENCES_UNITS_IMPERIAL;
import static com.example.realweather.repository.Constants.PREFERENCES_UNITS_KEY;

public interface PreferencesClient {


    default String getUnitUserPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREFERENCES_UNITS_KEY, PREFERENCES_UNITS_IMPERIAL);
    }

    default String[] getSelectedPosition(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String[] values = new String[3];
        values[0] = sharedPreferences.getString(Constants.PREFERENCES_SELECTED_PLACE_NAME_KEY, NO_PLACE);
        values[1] = sharedPreferences.getString(Constants.PREFERENCES_SELECTED_PLACE_LATITUDE_KEY, NO_LATITUDE);
        values[2] = sharedPreferences.getString(Constants.PREFERENCES_SELECTED_PLACE_LONGTITUDE_KEY, NO_LONGITUDE);
        return values;
    }

    default boolean isMetric(Context context) {
        return UnitType.METRIC.equals(getUnitUserPreference(context));
    }

    default void saveUnitUserPreference(Context context, @UnitType String unit) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREFERENCES_UNITS_KEY, unit)
                .apply();

    }
}
