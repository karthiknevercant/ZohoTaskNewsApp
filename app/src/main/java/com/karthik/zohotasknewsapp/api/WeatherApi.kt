package com.karthik.zohotasknewsapp.api

import com.karthik.zohotasknewsapp.NewsResponse
import com.karthik.zohotasknewsapp.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("https://api.openweathermap.org/data/2.5/weather")
    suspend fun getWeatherInfo(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String
    ): Response<WeatherResponse>
}
