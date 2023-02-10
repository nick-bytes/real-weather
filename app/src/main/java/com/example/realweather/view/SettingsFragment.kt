package com.example.realweather.view

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.realweather.R
import com.example.realweather.repository.PreferencesClient
import com.example.realweather.viewmodel.SettingsViewModel

class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener, PreferencesClient {
    private val viewModel: SettingsViewModel by viewModels()

    // TODO: 1/8/2020 change action bar to back button
    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.pref_general)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                NavHostFragment.findNavController(this@SettingsFragment).navigate(R.id.dashboardFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onResume() {
        super.onResume()
        val preference = findPreference<EditTextPreference>(getString(R.string.pref_zip_key))
        preference?.setOnBindEditTextListener { editText: EditText ->
            editText.inputType = InputType.TYPE_CLASS_NUMBER
            editText.selectAll()
            val maxLength = 5
            editText.filters = arrayOf<InputFilter>(LengthFilter(maxLength))
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if ("pref_zip_key" == key) {
            sharedPreferences.getString(key, "21215")
                    ?.let { viewModel.reinitializeWeatherData(Integer.parseInt(it)) }

        }
    }

    override fun onStart() {
        super.onStart()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        super.onStop()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }
}
