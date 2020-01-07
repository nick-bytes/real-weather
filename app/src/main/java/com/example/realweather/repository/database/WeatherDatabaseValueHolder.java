package com.example.realweather.repository.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.realweather.repository.database.WeatherDatabase;

public enum WeatherDatabaseValueHolder implements ChangeableValueHolder<WeatherDatabase> {

    INSTANCE {

        private transient WeatherDatabase database;

        @Override
        @Nullable
        public WeatherDatabase getValue() {
            return database;
        }

        @Override
        public void setValue(@NonNull WeatherDatabase value) {
            this.database = value;
        }

    }


}
