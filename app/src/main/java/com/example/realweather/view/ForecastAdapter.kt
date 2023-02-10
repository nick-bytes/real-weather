package com.example.realweather.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.realweather.R
import com.example.realweather.databinding.ItemForecastBinding
import com.example.realweather.model.DisplayValueConverter
import com.example.realweather.model.Forecast
import com.example.realweather.view.ForecastAdapter.ForecastAdapterViewHolder

class ForecastAdapter(private val metricPreference: Boolean) : RecyclerView.Adapter<ForecastAdapterViewHolder>() {
    private var list: List<Forecast>? = null
    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ForecastAdapterViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    fun setList(newList: List<Forecast>?) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemForecastBinding>(layoutInflater, R.layout.item_forecast, parent, false)
        return ForecastAdapterViewHolder(binding)
    }

    inner class ForecastAdapterViewHolder(private val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: Forecast?) {
            binding.converter = DisplayValueConverter(metricPreference)
            binding.model = forecast
        }
    }
}
