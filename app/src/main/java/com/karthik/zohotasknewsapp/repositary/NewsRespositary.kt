package com.karthik.zohotasknewsapp.repositary

import android.content.Context
import com.karthik.zohotasknewsapp.News
import com.karthik.zohotasknewsapp.api.NewsApi
import com.karthik.zohotasknewsapp.database.NewsDao
import com.karthik.zohotasknewsapp.utilities.NetworkManager.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRespositary @Inject constructor(
    private val newsApi: NewsApi,
    @ApplicationContext private val context: Context,
    private val newsDao: NewsDao
) {
    suspend fun getNewsList(): List<News> {
        lateinit var newsList: List<News>
        withContext(Dispatchers.IO) {
            if (isNetworkAvailable(context)) {
                val response = newsApi.getNewsList()

                response.body()?.let {
                    newsList = it.data
                    newsDao.deleteAllNews()
                    newsDao.addAllNews(newsList)
                }
            } else {
                newsList = newsDao.getAllNews()
            }
        }
        return newsList
    }
}