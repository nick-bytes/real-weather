package com.example.realweather.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.realweather.R;
import com.example.realweather.databinding.DashboardFragmentBinding;
import com.example.realweather.viewmodel.DashboardViewModel;

public class DashboardFragment extends Fragment {

    private final ForecastAdapter forecastAdapter = new ForecastAdapter();
    private DashboardViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        viewModel.scheduleJob(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DashboardFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment, container, false);
        binding.forecastRecyclerView.setAdapter(forecastAdapter);
        binding.forecastRecyclerView.setHasFixedSize(true);
        requireActivity().setTitle("RealWeather");
        viewModel.getForecast().observe(this, forecastAdapter::setList);
        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            NavHostFragment.findNavController(this).navigate(R.id.settingsFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
