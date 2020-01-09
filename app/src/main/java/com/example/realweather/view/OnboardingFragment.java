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
import com.example.realweather.databinding.OnboardingFragmentBinding;
import com.example.realweather.viewmodel.OnboardingViewModel;
import com.google.android.material.snackbar.Snackbar;

public class OnboardingFragment extends Fragment implements OnboardingCallback {

    private OnboardingViewModel viewModel;
    private OnboardingFragmentBinding binding;

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
    }

    private boolean isValidLength() {
        return binding.editText.getText().toString().length() == 5;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.onboarding_fragment, container, false);
        binding.setCallback(this);
        return binding.getRoot();
    }

    @Override
    public void onStartClicked(View view) {
        Context context = requireContext();
        if (!viewModel.isNetworkConnected(context)) {
            Snackbar.make(view, R.string.checkYourNetworkConnection, Snackbar.LENGTH_LONG).show();
            return;
        }
        if (!isValidLength()) {
            binding.editText.setError("Please enter a valid zip");
            return;
        }

        viewModel.handleOnboarding(context, binding.editText.getText().toString());
        NavHostFragment.findNavController(this).navigate(R.id.dashboardFragment);
    }


}