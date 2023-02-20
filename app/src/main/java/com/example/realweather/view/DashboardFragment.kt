package com.example.realweather.view

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import com.example.realweather.R
import com.example.realweather.databinding.DashboardFragmentBinding
import com.example.realweather.model.DisplayValueConverter
import com.example.realweather.model.Forecast
import com.example.realweather.model.TodayForecast
import com.example.realweather.repository.PreferencesClient
import com.example.realweather.viewmodel.DashboardViewModel
import com.google.android.gms.ads.AdRequest

class DashboardFragment : Fragment(), PreferencesClient {
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.settings) {
                    NavHostFragment.findNavController(this@DashboardFragment)
                        .navigate(R.id.settingsFragment)
                    return true
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<DashboardFragmentBinding>(
            inflater,
            R.layout.dashboard_fragment, container, false
        )
        val forecastAdapter = ForecastAdapter(getUnitUserPreference(requireContext()))
        binding.forecastRecyclerView.adapter = forecastAdapter
        binding.forecastRecyclerView.setHasFixedSize(true)
        viewModel.forecast.observe(viewLifecycleOwner) { newList: List<Forecast>? ->
            forecastAdapter.setList(
                newList
            )
        }
        viewModel.todayForecast.observe(viewLifecycleOwner) { forecast: TodayForecast? ->
            if (forecast == null) return@observe
            binding.model = forecast
            binding.converter = DisplayValueConverter(getUnitUserPreference(requireContext()))
        }
        initializeAds(binding)
        return binding.root
    }

    private fun initializeAds(binding: DashboardFragmentBinding) {
        val adRequest = AdRequest.Builder()
            .build()
        binding.adView.loadAd(adRequest)
    }
}
