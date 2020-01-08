package com.example.realweather.view;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.realweather.R;
import com.example.realweather.databinding.ItemForecastBinding;
import com.example.realweather.model.Forecast;
import com.example.realweather.model.IconConverter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

	class ForecastAdapterViewHolder extends RecyclerView.ViewHolder {

		final ItemForecastBinding binding;

		ForecastAdapterViewHolder(ItemForecastBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		void bind(Forecast forecast) {
			binding.setConverter(new IconConverter());
			binding.setModel(forecast);
		}
	}

	private final List<Forecast> list = new ArrayList<>();

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void onBindViewHolder(@NonNull ForecastAdapterViewHolder holder, int position) {
		holder.bind(list.get(position));
	}

	@NonNull
	@Override
	public ForecastAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		ItemForecastBinding binding =
				DataBindingUtil.inflate(layoutInflater, R.layout.item_forecast, parent, false);
		return new ForecastAdapterViewHolder(binding);
	}

	void setList(List<Forecast> newList) {
		list.clear();
		list.addAll(newList);
		notifyDataSetChanged();
	}
}
