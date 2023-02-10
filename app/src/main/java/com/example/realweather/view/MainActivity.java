package com.example.realweather.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.realweather.R;
import com.example.realweather.databinding.MainActivityBinding;
import com.example.realweather.repository.WeatherJobWorker;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        setSupportActionBar(binding.toolbar);
        WeatherJobWorker.scheduleJob(this);
	}

}
