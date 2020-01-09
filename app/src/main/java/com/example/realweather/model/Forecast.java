package com.example.realweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
	@SerializedName("dt_txt")
	private String date;
	@PrimaryKey(autoGenerate = true)
	private int id;
	@ColumnInfo
	@TypeConverters(DataTypeConverter.class)
	private Main main;
	@ColumnInfo
	@TypeConverters(DataTypeConverter.class)
	private List<Weather> weather;

	public Forecast() {
	}

	protected Forecast(Parcel in) {
		id = in.readInt();
		weather = in.readParcelable(Weather.class.getClassLoader());
		main = in.readParcelable(Main.class.getClassLoader());
		date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

	public int getId() {
        return id;
    }

	public Main getMain() {
		return main;
	}

	public List<Weather> getWeather() {
		return weather;
    }

	public void setId(int id) {
		this.id = id;
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
    }

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeList(weather);
		dest.writeParcelable(main, flags);
		dest.writeString(date);
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
