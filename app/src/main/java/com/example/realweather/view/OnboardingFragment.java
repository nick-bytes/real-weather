package com.example.realweather.view;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.realweather.R;
import com.example.realweather.databinding.OverviewFragmentBinding;
import com.example.realweather.viewmodel.OnboardingViewModel;


// TODO: 1/6/2020 1. Get zip from edittext
public class OnboardingFragment extends Fragment {

    private OnboardingViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(OnboardingViewModel.class);
        considerNavigatingToDashboard();
    }

    private void considerNavigatingToDashboard() {
        Context context = requireContext();
        if (viewModel.hasBeenOnboarded(context)) {
            NavHostFragment.findNavController(this).navigate(R.id.dashboardFragment);
        }
        viewModel.markAsOnboarded(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        OverviewFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.overview_fragment, container, false);
        return binding.getRoot();
    }


}