package com.example.realweather.model;

import androidx.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@StringDef(value = {WeatherDescription.LIGHT_RAIN, WeatherDescription.RAIN, WeatherDescription.CLEAR, WeatherDescription.FEW_CLOUD,
        WeatherDescription.BROKEN_CLOUDS, WeatherDescription.CLOUD, WeatherDescription.FOG, WeatherDescription.SNOW, WeatherDescription.STORM})
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.SOURCE)
public @interface WeatherDescription {

    String LIGHT_RAIN = "light rain";
    String RAIN = "rain";
    String CLEAR = "clear";
    String FEW_CLOUD = "few cloud";
    String BROKEN_CLOUDS = "broken clouds";
    String CLOUD = "cloud";
    String FOG = "fog";
    String SNOW = "snow";
    String STORM = "storm";


}