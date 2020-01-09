package com.example.realweather.model;

import android.util.ArrayMap;

import com.example.realweather.R;

public class DisplayValueConverter {

	private static final ArrayMap<String, Integer> ICON_MAPPING = createMapping();
	private boolean metricPreference;

	static ArrayMap<String, Integer> createMapping() {
		ArrayMap<String, Integer> mapping = new ArrayMap<>();
		mapping.put(WeatherDescription.CLEAR, R.drawable.ic_clear);
		mapping.put(WeatherDescription.LIGHT_RAIN, R.drawable.ic_light_rain);
		mapping.put(WeatherDescription.FOG, R.drawable.ic_fog);
		mapping.put(WeatherDescription.SNOW, R.drawable.ic_snow);
		mapping.put(WeatherDescription.STORM, R.drawable.ic_storm);
		mapping.put(WeatherDescription.FEW_CLOUD, R.drawable.ic_light_clouds);
		mapping.put(WeatherDescription.BROKEN_CLOUDS, R.drawable.ic_light_clouds);

		return mapping;
	}

	public DisplayValueConverter(boolean metricPreference) {
		this.metricPreference = metricPreference;
	}

	public int getIcon(String weatherDescription) {
		for (String key : ICON_MAPPING.keySet()) {
			if (key.contains(weatherDescription)) return ICON_MAPPING.get(key);
		}
		return R.drawable.example_appwidget_preview;
	}

	public String formatTemperature(double temperatureInKelvin) {
		return metricPreference ? kelvinToCelsius(temperatureInKelvin) : kelvinToFarenheit(temperatureInKelvin);
	}

	private String kelvinToCelsius(double temperatureInKelvin) {
		int result = (int) Math.round(temperatureInKelvin - 273.15);
		return result + "\u00B0";
	}

	private String kelvinToFarenheit(double temperatureInKelvin) {
		int result = (int) Math.round((temperatureInKelvin - 273.15) * 1.8 + 32);
		return result + "\u00B0";
	}

}
