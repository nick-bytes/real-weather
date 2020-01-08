package com.example.realweather.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.realweather.R;
import com.example.realweather.repository.WeatherJobService;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DataBindingUtil.setContentView(this, R.layout.main_activity);
		WeatherJobService.scheduleJob(this);
	}

}
