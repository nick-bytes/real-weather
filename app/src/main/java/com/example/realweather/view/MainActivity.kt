package com.example.realweather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.realweather.R
import com.example.realweather.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
        setSupportActionBar(binding.toolbar)
        lifecycle.addObserver(ActivityLifecycleObserver())
    }
}
