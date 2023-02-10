package com.example.realweather.repository.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class RetrofitClient {
    companion object {


        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private var INSTANCE: Retrofit? = null
        val instance: Retrofit?
            get() {
                if (INSTANCE == null) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
                    INSTANCE = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
                return INSTANCE
            }
    }
}
