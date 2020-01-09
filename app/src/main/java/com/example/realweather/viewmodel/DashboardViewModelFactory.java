package com.example.realweather.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;

public class DashboardViewModelFactory implements Factory {

    private final int zip;

    public DashboardViewModelFactory(int zip) {
        this.zip = zip;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }
}
