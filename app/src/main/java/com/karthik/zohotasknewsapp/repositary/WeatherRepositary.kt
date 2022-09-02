package com.karthik.zohotasknewsapp.repositary

import android.content.Context
import com.karthik.zohotasknewsapp.WeatherResponse
import com.karthik.zohotasknewsapp.api.WeatherApi
import com.karthik.zohotasknewsapp.utilities.Constants
import com.karthik.zohotasknewsapp.utilities.NetworkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositary @Inject constructor(
    private val weatherApi: WeatherApi,
    @ApplicationContext private val context: Context
) {
    suspend fun getWeatherInfo(lat: Double, lon: Double): Response<WeatherResponse>? {
        var response: Response<WeatherResponse>? = null
        withContext(Dispatchers.IO) {
            if (NetworkManager.isNetworkAvailable(context)) {
                response = weatherApi.getWeatherInfo(lat, lon, Constants.WEATHER_API_KEY)
            }
        }
        return response
    }
}