package com.karthik.zohotasknewsapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.karthik.zohotasknewsapp.News
import com.karthik.zohotasknewsapp.WeatherResponse
import com.karthik.zohotasknewsapp.repositary.NewsRespositary
import com.karthik.zohotasknewsapp.repositary.WeatherRepositary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRespositary: NewsRespositary,
    private val weatherRepositary: WeatherRepositary
) :
    BaseViewModel() {

    val newsList = MutableLiveData<List<News>>()
    val filteredList = MutableLiveData<List<News>>()
    val WeatherResponse = MutableLiveData<WeatherResponse>()
    var noDataMsg = MutableLiveData<Boolean>()

    init {
        getNewsList()
    }

    fun getNewsList() {
        viewModelScope.launch() {
            try {
                showLoader.value = true
                val response = newsRespositary.getNewsList()
                showLoader.value = false

                if (response.isNotEmpty()) {
                    newsList.postValue(response)
                    filteredList.postValue(response)

                    noDataMsg.postValue(false)
                } else {
                    noDataMsg.postValue(true)
                }
            } catch (e: Exception) {
                Log.d("Exception", e.message.toString())
            }
        }
    }

    fun filterNews(searchText: String?) {
        if (!searchText.isNullOrBlank() && !newsList.value.isNullOrEmpty()) {
            filteredList.value = newsList.value?.filter {
                it.title.contains(
                    searchText.trim(),
                    true
                ) || it.content.contains(searchText.trim(), true)
            }
        } else if (!newsList.value.isNullOrEmpty())
            filteredList.value = newsList.value
    }

    fun getWeatherInfo(lat: Double, lon: Double) {
        viewModelScope.launch() {
            try {

                val response = weatherRepositary.getWeatherInfo(lat, lon)

                if (response?.body() is WeatherResponse) {
                    response.body().let {
                        WeatherResponse.postValue(it)
                    }
                }
            } catch (e: Exception) {
                Log.d("Exception", e.message.toString())
            }
        }
    }
}