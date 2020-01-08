package com.example.realweather.view;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.example.realweather.R;
import com.example.realweather.repository.PreferencesClient;
import com.example.realweather.viewmodel.SettingsViewModel;

/**
 * The SettingsFragment serves as the display for all of the user's settings. In Sunshine, the
 * user will be able to change their preference for units of measurement from metric to imperial,
 * set their preferred weather location, and indibcate whether or not they'd like to see
 * notifications.
 *
 * Please note: If you are using our dummy weather services, the location returned will always be
 * Mountain View, California.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements
		SharedPreferences.OnSharedPreferenceChangeListener, PreferencesClient {

	private SettingsViewModel viewModel;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		viewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
	}

	@Override
	public void onCreatePreferences(Bundle bundle, String s) {
		addPreferencesFromResource(R.xml.pref_general);
		SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
		PreferenceScreen prefScreen = getPreferenceScreen();
		int count = prefScreen.getPreferenceCount();
		for (int i = 0; i < count; i++) {
			Preference preference = prefScreen.getPreference(i);
			if (!(preference instanceof CheckBoxPreference)) {
				String value = sharedPreferences.getString(preference.getKey(), "");
				setPreferenceSummary(preference, value);
			}
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		switch (key) {
			case "location":
				viewModel.updateLocationPreference(requireActivity());
				break;
			case "units":
				viewModel.updateUnitPreference(requireActivity());
				break;
			default:
				Preference preference = findPreference(key);
				if (null != preference && !(preference instanceof CheckBoxPreference)) {
					setPreferenceSummary(preference, sharedPreferences.getString(key, ""));
				}
				break;
		}

	}

	@Override
	public void onStart() {
		super.onStart();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	// TODO: 1/7/2020 need?
	private void setPreferenceSummary(Preference preference, Object value) {
		String stringValue = value.toString();

		if (preference instanceof ListPreference) {
			// For list preferences, look up the correct display value in
			// the preference's 'entries' list (since they have separate labels/values).
			ListPreference listPreference = (ListPreference) preference;
			int prefIndex = listPreference.findIndexOfValue(stringValue);
			if (prefIndex >= 0) {
				preference.setSummary(listPreference.getEntries()[prefIndex]);
			}
		} else {
			// For other preferences, set the summary to the value's simple string representation.
			preference.setSummary(stringValue);
		}
	}
}
