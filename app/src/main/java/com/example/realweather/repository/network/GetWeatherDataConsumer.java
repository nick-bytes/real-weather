package com.example.realweather.repository.network;

/**
 * API for consumers of this Retrofit instance
 *
 * @author Nick Emerson
 */
public interface GetWeatherDataConsumer {

	default GetWeatherDataService getService() {
		return RetrofitClient.Companion.getInstance().create(GetWeatherDataService.class);
	}
}
