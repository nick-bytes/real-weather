package com.example.realweather.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable {

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
		@Override
		public Weather createFromParcel(Parcel in) {
			return new Weather(in);
		}

		@Override
		public Weather[] newArray(int size) {
			return new Weather[size];
		}
	};
	private String description;
	private String icon;
	private float id;
	private String main;

	protected Weather(Parcel in) {
		id = in.readFloat();
		main = in.readString();
		description = in.readString();
		icon = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public String getDescription() {
		return description;
	}

	public String getIcon() {
		return icon;
	}

	public float getId() {
		return id;
	}

	public String getMain() {
		return main;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setId(float id) {
		this.id = id;
	}

	public void setMain(String main) {
		this.main = main;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(id);
		dest.writeString(main);
		dest.writeString(description);
		dest.writeString(icon);
	}
}