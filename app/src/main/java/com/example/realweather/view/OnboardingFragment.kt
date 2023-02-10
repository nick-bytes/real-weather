package com.example.realweather.view

import android.app.Activity
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.realweather.R
import com.example.realweather.databinding.OnboardingFragmentBinding
import com.example.realweather.viewmodel.OnboardingViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import java.util.*

class OnboardingFragment : Fragment(), OnboardingCallback {
    private val viewModel: OnboardingViewModel by viewModels()
    private var binding: OnboardingFragmentBinding? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OnboardingViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        considerNavigatingToDashboard()
    }

    private fun considerNavigatingToDashboard() {
        val context = requireContext()
        if (viewModel!!.hasBeenOnboarded(context)) {
            NavHostFragment.findNavController(this).navigate(R.id.dashboardFragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.onboarding_fragment, container, false)
        binding.setCallback(this)
        binding.editText.addTextChangedListener(createTextWatcher())
        return binding.getRoot()
    }

    private fun createTextWatcher(): TextWatcher {
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
                if (!viewModel!!.isNetworkConnected(context)) {
                    handleLocationError()
                    return
                }
                binding!!.button.visibility = View.INVISIBLE
                Handler().postDelayed({
                    viewModel!!.handleOnboarding(context, binding!!.editText.text.toString())
                    NavHostFragment.findNavController(this@OnboardingFragment).navigate(R.id.dashboardFragment)
                }, 1000)
            }
        }
    }

    override fun onEnableLocationClick(view: View) {
        val context = requireContext()
        if (!viewModel!!.isNetworkConnected(context)) {
            Snackbar.make(view, R.string.checkYourNetworkConnection, Snackbar.LENGTH_LONG).show()
            return
        }
        viewModel!!.requestPermissions(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (OnboardingViewModel.REQUEST_LOCATION_PERMISSION == requestCode) {
            val activity: Activity = requireActivity()
            val task = fusedLocationClient!!.lastLocation
            task.addOnSuccessListener(activity) { location: Location? ->
                if (location != null) {
                    val addresses = getAddressFromLocation(location)
                    if (addresses == null || addresses.isEmpty()) {
                        handleLocationError()
                        return@addOnSuccessListener
                    }
                    viewModel!!.handleOnboarding(activity, addresses[0].postalCode)
                    NavHostFragment.findNavController(this).navigate(R.id.dashboardFragment)
                    return@addOnSuccessListener
                }
                handleLocationError()
            }
            task.addOnFailureListener(activity) { e: Exception? -> handleLocationError() }
        }
    }

    private fun getAddressFromLocation(location: Location): List<Address>? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        var addresses: List<Address>? = null
        try {
            addresses = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1)
        } catch (exception: Exception) {
            handleLocationError()
        }
        return addresses
    }

    private fun handleLocationError() {
        Snackbar.make(binding!!.root, R.string.failedGettingLocation, Snackbar.LENGTH_LONG).show()
    }
}
