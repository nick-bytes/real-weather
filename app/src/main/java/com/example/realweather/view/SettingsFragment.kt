package com.example.realweather.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.realweather.R;
import com.example.realweather.repository.PreferencesClient;
import com.example.realweather.viewmodel.SettingsViewModel;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat implements
		SharedPreferences.OnSharedPreferenceChangeListener, PreferencesClient {

	private SettingsViewModel viewModel;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		viewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
	}

	// TODO: 1/8/2020 change action bar to back button
	@Override
	public void onCreatePreferences(Bundle bundle, String s) {
		addPreferencesFromResource(R.xml.pref_general);
	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
			@Override
			public void handleOnBackPressed() {
				NavHostFragment.findNavController(SettingsFragment.this).navigate(R.id.dashboardFragment);
			}
		};
		requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
	}

	@Override
	public void onResume() {
		super.onResume();
		EditTextPreference preference = findPreference(getString(R.string.pref_zip_key));
		Objects.requireNonNull(preference).setOnBindEditTextListener(editText -> {
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			editText.selectAll();
			int maxLength = 5;
			editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
		});
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if ("pref_zip_key".equals(key)) {
			viewModel.reinitializeWeatherData(Integer.valueOf(sharedPreferences.getString(key, "21215")));
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

}
