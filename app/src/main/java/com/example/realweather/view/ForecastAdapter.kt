package com.example.realweather.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realweather.R;
import com.example.realweather.databinding.ItemForecastBinding;
import com.example.realweather.model.DisplayValueConverter;
import com.example.realweather.model.Forecast;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

	private List<Forecast> list;
	private boolean metricPreference;

	public ForecastAdapter(boolean metricPreference) {
		this.metricPreference = metricPreference;
	}

	@Override
	public int getItemCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public void onBindViewHolder(@NonNull ForecastAdapterViewHolder holder, int position) {
		holder.bind(list.get(position));
	}

	void setList(List<Forecast> newList) {
		list = newList;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public ForecastAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		ItemForecastBinding binding =
				DataBindingUtil.inflate(layoutInflater, R.layout.item_forecast, parent, false);
		return new ForecastAdapterViewHolder(binding);
	}

	class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {

		final ItemForecastBinding binding;

		ForecastAdapterViewHolder(ItemForecastBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		void bind(Forecast forecast) {
			binding.setConverter(new DisplayValueConverter(metricPreference));
			binding.setModel(forecast);
		}
	}
}
