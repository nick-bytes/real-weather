package com.example.realweather.model;

import android.util.ArrayMap;

import com.example.realweather.R;

public class IconConverter {

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
	ArrayMap<String, Integer> ICON_MAPPING = createMapping();

	public int getIcon(String weatherDescription) {
		for (String key : ICON_MAPPING.keySet()) {
			if (key.contains(weatherDescription)) return ICON_MAPPING.get(key);
		}
		return R.drawable.example_appwidget_preview;
	}

}
