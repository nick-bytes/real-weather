package com.example.realweather.repository;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    @BindingAdapter("app:icon")
    public static void setIcon(ImageView view, int resId) {
        view.setImageResource(resId);
    }
}
