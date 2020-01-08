package com.example.realweather.repository;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 21/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class holds constant strings used across the project
 */
public class Constants {


    public static final String DETAILED_FORECAST_ARGUMENT_KEY = "detailed_weather_object_key";

    //last updated key
    public static final String PREFERENCES_SAVE_LAST_UPDATED_KEY = "last_updated_key";
    //selected place keys
    public static final String PREFERENCES_SELECTED_PLACE_NAME_KEY = "selected_place_name_key";
    public static final String PREFERENCES_SELECTED_PLACE_LATITUDE_KEY = "selected_place_lat_key";
    public static final String PREFERENCES_SELECTED_PLACE_LONGTITUDE_KEY = "selected_place_lon_key";
    //current place keys
    public static final String PREFERENCES_CURRENT_PLACE_NAME_KEY = "current_place_name_key";
    public static final String PREFERENCES_CURRENT_PLACE_LATITUDE_KEY = "current_place_lat_key";
    public static final String PREFERENCES_CURRENT_PLACE_LONGTITUDE_KEY = "current_place_lon_key";
    public static final String ONBOARDED_PREFERENCE_KEY = "ONBOARDED_PREFERENCE_KEY";
    //units preference keys
    public static final String PREFERENCES_UNITS_KEY = "app_units_preference_key";
    public static final String PREFERENCES_UNITS_IMPERIAL = "IMPERIAL";
    public static final String PREFERENCES_UNITS_METRIC = "METRIC";

    //connectivity key
    public static final String PREFERENCES_CONNECTIVITY_KEY = "connectivity_key";

    //FragmentMyPolygons.java Constants

    //polygon list is synced
    public static final String PREFERENCES_IS_POLYGON_LIST_SYNCED = "polygon_sync_boolean_key";

    //error strings
    public static final String NO_PLACE ="Unknown name";
    public static final String NO_LATITUDE ="Unknown lat";
    public static final String NO_LONGITUDE ="Unknown lon";

    //FragmentErrorLayout.java

    //bundle key
    public static final String FRAGMENT_ERROR_LAYOUT_BUNDLE_KEY = "error_layout_bundle_key";
    public static final String FRAGMENT_ERROR_LAYOUT_TEXT_BUNDLE_KEY = "error_layout_text_bundle_key";

}
