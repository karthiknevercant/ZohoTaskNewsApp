package com.karthik.zohotasknewsapp.api

import com.karthik.zohotasknewsapp.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/news")
    suspend fun getNewsList(@Query("category") category: String = "all"): Response<NewsResponse>
}