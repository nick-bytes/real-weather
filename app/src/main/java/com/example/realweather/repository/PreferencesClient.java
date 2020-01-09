package com.example.realweather.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.realweather.R;
import com.example.realweather.model.UnitType;

import static com.example.realweather.repository.Constants.NO_LATITUDE;
import static com.example.realweather.repository.Constants.NO_LONGITUDE;
import static com.example.realweather.repository.Constants.NO_PLACE;
import static com.example.realweather.repository.Constants.PREFERENCES_UNITS_KEY;

public interface PreferencesClient {


	default String[] getSelectedPosition(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String[] values = new String[3];
        values[0] = sharedPreferences.getString(Constants.PREFERENCES_SELECTED_PLACE_NAME_KEY, NO_PLACE);
        values[1] = sharedPreferences.getString(Constants.PREFERENCES_SELECTED_PLACE_LATITUDE_KEY, NO_LATITUDE);
        values[2] = sharedPreferences.getString(Constants.PREFERENCES_SELECTED_PLACE_LONGTITUDE_KEY, NO_LONGITUDE);
        return values;
    }

	default boolean getUnitUserPreference(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(context.getString(R.string.pref_units_key), false);
	}

	default int getUserZipPreference(Context context) {
		return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context)
				.getString(context.getString(R.string.pref_zip_key), "21215"));
	}

	default boolean hasBeenOnboarded(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.pref_onboarded_key), false);
	}

	default boolean isMetric(Context context) {
        return UnitType.METRIC.equals(getUnitUserPreference(context));
    }

	default void markAsOnboarded(Context context) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(context.getString(R.string.pref_onboarded_key), true).apply();
	}

	default void resetLocationCoordinates(Context context) {

	}

	default void saveUnitUserPreference(Context context, @UnitType String unit) {
		PreferenceManager.getDefaultSharedPreferences(context)
				.edit()
				.putString(PREFERENCES_UNITS_KEY, unit)
				.apply();

	}

	@SuppressLint("ApplySharedPref")
	default void saveUserZip(Context context, String zip) {
		PreferenceManager.getDefaultSharedPreferences(context)
				.edit()
				.putString(context.getString(R.string.pref_zip_key), zip)
				.commit();

	}
}
