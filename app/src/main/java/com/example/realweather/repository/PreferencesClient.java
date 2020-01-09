package com.example.realweather.repository;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.preference.PreferenceManager;

import com.example.realweather.R;

public interface PreferencesClient {


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
	default void markAsOnboarded(Context context) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(context.getString(R.string.pref_onboarded_key), true).apply();
	}

	default void resetLocationCoordinates(Context context) {

	}

	@SuppressLint("ApplySharedPref")
	default void saveUserZip(Context context, String zip) {
		PreferenceManager.getDefaultSharedPreferences(context)
				.edit()
				.putString(context.getString(R.string.pref_zip_key), zip)
				.commit();

	}
}
