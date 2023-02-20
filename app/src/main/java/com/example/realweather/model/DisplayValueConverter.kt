package com.example.realweather.model

import android.util.ArrayMap
import com.example.realweather.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.math.roundToInt

class DisplayValueConverter(private val metricPreference: Boolean) {
    fun getIcon(weatherDescription: String?): Int {
        for (key in ICON_MAPPING.keys) {
            weatherDescription?.let {
                if (weatherDescription.contains(key)) return ICON_MAPPING.getValue(key)
            }
        }
        return R.drawable.ic_light_clouds
    }

    fun formatTemperature(temperatureInKelvin: Double): String {
        return if (metricPreference) kelvinToCelsius(temperatureInKelvin) else kelvinToFahrenheit(
            temperatureInKelvin
        )
    }

    fun formatDescription(description: String): String {
        return description.split(' ').joinToString(" ") { it.replaceFirstChar(Char::titlecase) }
    }

    fun formatHumidity(humidity: Double): String {
        return "${humidity.roundToInt()}% RH"
    }


    private fun kelvinToCelsius(temperatureInKelvin: Double): String {
        val result = (temperatureInKelvin - 273.15).roundToInt()
        return result.toString() + "\u00B0"
    }

    private fun kelvinToFahrenheit(temperatureInKelvin: Double): String {
        val result = ((temperatureInKelvin - 273.15) * 1.8 + 32).roundToInt()
        return result.toString() + "\u00B0"
    }

    fun formatTime(time: String?): String {
        return LocalTime.parse(time).format(DateTimeFormatter.ofPattern("h:mma"))
    }

    fun formatDate(date: String?): String {
        val localDate = LocalDate.parse(
            date,
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        ) // LocalDate = 2010-02-23
        val weekday = localDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US) // String = Tue
        val month = localDate.month.getDisplayName(TextStyle.SHORT, Locale.US)
        val day = localDate.dayOfMonth
        return "$weekday, $month $day"
    }

    companion object {
        private val ICON_MAPPING = createMapping().withDefault { R.drawable.ic_light_clouds }
        private fun createMapping(): ArrayMap<String, Int> {
            val mapping = ArrayMap<String, Int>()
            mapping[WeatherDescription.CLEAR] = R.drawable.ic_clear
            mapping[WeatherDescription.LIGHT_RAIN] = R.drawable.ic_light_rain
            mapping[WeatherDescription.FOG] = R.drawable.ic_fog
            mapping[WeatherDescription.SNOW] = R.drawable.ic_snow
            mapping[WeatherDescription.STORM] = R.drawable.ic_storm
            mapping[WeatherDescription.FEW_CLOUD] = R.drawable.ic_light_clouds
            mapping[WeatherDescription.BROKEN_CLOUDS] = R.drawable.ic_light_clouds
            return mapping
        }
    }
}
