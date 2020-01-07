package com.example.realweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "forecast")
public class Forecast implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Forecast> CREATOR = new Parcelable.Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("weather")
    private WeatherSummary summary;
    @SerializedName("main")
    private Main main;
    @SerializedName("dt_txt")
    private String date;

    protected Forecast(Parcel in) {
        // TODO: 1/6/2020
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO: 1/6/2020
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WeatherSummary getWeatherSummary() {
        return summary;
    }

    public void setWeatherSummary(WeatherSummary summary) {
        this.summary = summary;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
