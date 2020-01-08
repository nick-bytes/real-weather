package com.example.realweather.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Main implements Parcelable {

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Main> CREATOR = new Parcelable.Creator<Main>() {
		@Override
		public Main createFromParcel(Parcel in) {
			return new Main(in);
		}

		@Override
		public Main[] newArray(int size) {
			return new Main[size];
		}
	};
	@SerializedName("feels_like")
	private double feelsLike;
	private double humidity;
	@SerializedName("temp_max")
	private double maxTemp;
	@SerializedName("temp_min")
	private double minTemp;
	private double pressure;
	private double temp;

	protected Main(Parcel in) {
		temp = in.readDouble();
		feelsLike = in.readDouble();
		minTemp = in.readDouble();
		maxTemp = in.readDouble();
		pressure = in.readDouble();
		humidity = in.readDouble();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public double getFeelsLike() {
		return feelsLike;
	}

	public double getHumidity() {
		return humidity;
	}

	public double getMaxTemp() {
		return maxTemp;
	}

	public double getMinTemp() {
		return minTemp;
	}

	public double getPressure() {
		return pressure;
	}

	public double getTemp() {
		return temp;
	}

	public void setFeelsLike(double feelsLike) {
		this.feelsLike = feelsLike;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}

	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(temp);
		dest.writeDouble(feelsLike);
		dest.writeDouble(minTemp);
		dest.writeDouble(maxTemp);
		dest.writeDouble(pressure);
		dest.writeDouble(humidity);
	}
}