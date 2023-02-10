package com.example.realweather.view;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

import static com.example.realweather.viewmodel.OnboardingViewModel.REQUEST_LOCATION_PERMISSION;

public class OnboardingFragment extends Fragment implements OnboardingCallback {

    private OnboardingViewModel viewModel;
    private OnboardingFragmentBinding binding;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(OnboardingViewModel.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        considerNavigatingToDashboard();
    }

    private void considerNavigatingToDashboard() {
        Context context = requireContext();
        if (viewModel.hasBeenOnboarded(context)) {
            NavHostFragment.findNavController(this).navigate(R.id.dashboardFragment);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.onboarding_fragment, container, false);
        binding.setCallback(this);
        binding.editText.addTextChangedListener(createTextWatcher());
        return binding.getRoot();
    }


    @NotNull
    private TextWatcher createTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() != 5) return;
                Context context = requireContext();
                if (!viewModel.isNetworkConnected(context)) {
                    handleLocationError();
                    return;
                }
                binding.button.setVisibility(View.INVISIBLE);
                new Handler().postDelayed(() -> {
                    viewModel.handleOnboarding(context, binding.editText.getText().toString());
                    NavHostFragment.findNavController(OnboardingFragment.this).navigate(R.id.dashboardFragment);
                }, 1000);
            }
        };
    }

    @Override
    public void onEnableLocationClicked(View view) {
        Context context = requireContext();
        if (!viewModel.isNetworkConnected(context)) {
            Snackbar.make(view, R.string.checkYourNetworkConnection, Snackbar.LENGTH_LONG).show();
            return;
        }

        viewModel.requestPermissions(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((REQUEST_LOCATION_PERMISSION == requestCode)) {
            Activity activity = requireActivity();
            Task<Location> task = fusedLocationClient.getLastLocation();
            task.addOnSuccessListener(activity, location -> {
                if (location != null) {
                    List<Address> addresses = getAddressFromLocation(location);
                    if (addresses == null || addresses.isEmpty()) {
                        handleLocationError();
                        return;
                    }
                    viewModel.handleOnboarding(activity, addresses.get(0).getPostalCode());
                    NavHostFragment.findNavController(this).navigate(R.id.dashboardFragment);
                    return;
                }
                handleLocationError();
            });

            task.addOnFailureListener(activity, e -> handleLocationError());
        }

    }

    private List<Address> getAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (Exception exception) {
            handleLocationError();
        }
        return addresses;
    }

    private void handleLocationError() {
        Snackbar.make(binding.getRoot(), R.string.failedGettingLocation, Snackbar.LENGTH_LONG).show();
    }


}