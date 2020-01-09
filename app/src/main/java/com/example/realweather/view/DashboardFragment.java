package com.example.realweather.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.realweather.R;
import com.example.realweather.databinding.DashboardFragmentBinding;
import com.example.realweather.model.DisplayValueConverter;
import com.example.realweather.repository.PreferencesClient;
import com.example.realweather.viewmodel.DashboardViewModel;

public class DashboardFragment extends Fragment implements PreferencesClient {

    private DashboardViewModel viewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DashboardFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.dashboard_fragment, container, false);
        ForecastAdapter forecastAdapter = new ForecastAdapter(getUnitUserPreference(requireContext()));
        binding.forecastRecyclerView.setAdapter(forecastAdapter);
        binding.forecastRecyclerView.setHasFixedSize(true);
        requireActivity().setTitle(R.string.appName);
        setupViewModel();
        viewModel.getForecast().observe(this, forecastAdapter::setList);
        viewModel.getTodayForecast().observe(this, forecast -> {
            if (forecast == null) return;
            binding.setModel(forecast);
            binding.setConverter(new DisplayValueConverter(getUnitUserPreference(requireContext())));
        });
        setHasOptionsMenu(true);
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

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }


}
