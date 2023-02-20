package com.example.realweather.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.realweather.R
import com.example.realweather.databinding.OnboardingFragmentBinding
import com.example.realweather.viewmodel.OnboardingViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class OnboardingFragment : Fragment(), OnboardingCallback {
    private val viewModel: OnboardingViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                handleLocationError()
                return@registerForActivityResult
            }
            with(fusedLocationClient.lastLocation) {
                val activity = requireActivity()
                addOnSuccessListener(activity) { location: Location? ->
                    location?.let {
                        lifecycleScope.launch(Dispatchers.Default) {
                            val locationJob = async { getAddressFromLocation(location) }
                            val addresses = locationJob.await()
                            if (addresses.isNullOrEmpty()) {
                                handleLocationError()
                                return@launch
                            }
                            withContext(Dispatchers.Main) {
                                viewModel.handleOnboarding(context, addresses[0].postalCode)
                                NavHostFragment.findNavController(this@OnboardingFragment)
                                    .navigate(R.id.dashboardFragment)
                            }
                        }
                    }
                }
                addOnFailureListener(activity) { handleLocationError() }
            }


        } else {
            handleLocationError()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        considerNavigatingToDashboard()
        startLocationPermissionRequest()
    }

    private fun considerNavigatingToDashboard() {
        val context = requireContext()
        if (viewModel.hasBeenOnboarded(context)) {
            NavHostFragment.findNavController(this).navigate(R.id.dashboardFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: OnboardingFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.onboarding_fragment, container, false)
        binding.callback = this
        binding.editText.addTextChangedListener(createTextWatcher(binding))
        return binding.root
    }

    private fun createTextWatcher(binding: OnboardingFragmentBinding): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //do nothing
            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().length != 5) return
                val context = requireContext()
                if (!viewModel.isNetworkConnected(context)) {
                    handleLocationError()
                    return
                }
                binding.button.visibility = View.INVISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModel.handleOnboarding(context, binding.editText.text.toString())
                    NavHostFragment.findNavController(this@OnboardingFragment)
                        .navigate(R.id.dashboardFragment)
                }, 1000)
            }
        }
    }

    override fun onEnableLocationClick(view: View) {
        val context = requireContext()
        if (!viewModel.isNetworkConnected(context)) {
            Snackbar.make(view, R.string.checkYourNetworkConnection, Snackbar.LENGTH_LONG).show()
            return
        }
        startLocationPermissionRequest()
    }

    private fun startLocationPermissionRequest() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun getAddressFromLocation(location: Location): List<Address>? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        var addresses: List<Address>? = null

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) return geocoder.getFromLocation(
            location.latitude,
            location.longitude,
            1,
        )
        try {
            geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1,
                @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                object : Geocoder.GeocodeListener {
                    override fun onGeocode(p0: MutableList<Address>) {
                        addresses = p0
                    }

                    override fun onError(errorMessage: String?) {
                        super.onError(errorMessage)
                        handleLocationError()
                    }
                })
        } catch (exception: Exception) {
            handleLocationError()
        }
        return addresses
    }

    private fun handleLocationError() {
        Toast.makeText(requireActivity(), R.string.failedGettingLocation, Toast.LENGTH_LONG).show()

    }
}
