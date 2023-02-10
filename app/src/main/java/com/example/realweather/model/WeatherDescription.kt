package com.example.realweather.model

import androidx.annotation.StringDef

@StringDef(value = [WeatherDescription.LIGHT_RAIN, WeatherDescription.RAIN, WeatherDescription.CLEAR, WeatherDescription.FEW_CLOUD, WeatherDescription.BROKEN_CLOUDS, WeatherDescription.CLOUD, WeatherDescription.FOG, WeatherDescription.SNOW, WeatherDescription.STORM])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class WeatherDescription {
    companion object {
        const val LIGHT_RAIN = "light rain"
        const val RAIN = "rain"
        const val CLEAR = "clear"
        const val FEW_CLOUD = "few cloud"
        const val BROKEN_CLOUDS = "broken clouds"
        const val CLOUD = "cloud"
        const val FOG = "fog"
        const val SNOW = "snow"
        const val STORM = "storm"
    }
}
