/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.realweather.repository;

import android.content.Context;
import android.util.ArrayMap;

import com.example.realweather.R;
import com.example.realweather.model.WeatherDescription;

public interface RealWeatherUtilConsumer {


    ArrayMap<String, Integer> ICON_MAPPING = createMapping();


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

    default double kevlinToCelcius(double temperatureInCelsius) {
        return (temperatureInCelsius * 1.8) + 32; //// TODO: 1/6/2020 wrong
    }

    default double kelvinToFarenheit(double temperatureInKelvin) {
        return (temperatureInKelvin - 273.15) * 1.8 + 32;
    }

    default String formatTemperature(Context context, double temperature) {
////        if (!WeatherPreferences.isMetric(context)) {
////            temperature = celsiusToFahrenheit(temperature);
////        }
////
////        int temperatureFormatResourceId = R.string.format_temperature;
//
//        /* For presentation, assume the user doesn't care about tenths of a degree. */
//        return String.format(context.getString(temperatureFormatResourceId), temperature);
        // TODO: 1/6/2020
        return null;
    }

    default String formatHumidity(double humidity) {
        return humidity + " %";
    }

    default String formatPressure(double pressure) {
        return pressure + " hPa";
    }


    default String formatHighLows(Context context, double high, double low) {
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String formattedHigh = formatTemperature(context, roundedHigh);
        String formattedLow = formatTemperature(context, roundedLow);

        String highLowStr = formattedHigh + " / " + formattedLow;
        return highLowStr;
    }

    default Integer getIcon(String weatherDescription) {
        for (String key : ICON_MAPPING.keySet()) {
            if (key.contains(weatherDescription)) return ICON_MAPPING.get(key);
        }
        return R.drawable.example_appwidget_preview;
    }

    default int getSmallArtResourceIdForWeatherCondition(int weatherId) {

        /*
         * Based on weather code data for Open Weather Map.
         */
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.ic_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.ic_rain;
        } else if (weatherId == 511) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.ic_fog;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.ic_storm;
        } else if (weatherId == 800) {
            return R.drawable.ic_clear;
        } else if (weatherId == 801) {
            return R.drawable.ic_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.ic_cloudy;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.drawable.ic_clear;
        }

        return R.drawable.ic_storm;
    }

    default int getLargeArtResourceIdForWeatherCondition(int weatherId) {

        /*
         * Based on weather code data for Open Weather Map.
         */
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.art_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.art_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.art_rain;
        } else if (weatherId == 511) {
            return R.drawable.art_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.art_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.art_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.art_fog;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.art_storm;
        } else if (weatherId == 800) {
            return R.drawable.art_clear;
        } else if (weatherId == 801) {
            return R.drawable.art_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.art_clouds;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.drawable.art_storm;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.drawable.art_storm;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.drawable.art_clear;
        }

        return R.drawable.art_storm;
    }


}