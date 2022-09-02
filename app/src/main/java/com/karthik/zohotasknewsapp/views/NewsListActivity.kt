package com.karthik.zohotasknewsapp

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.location.LocationServices
import com.karthik.zohotasknewsapp.adapters.NewsListAdapter
import com.karthik.zohotasknewsapp.databinding.ActivityNewsListBinding
import com.karthik.zohotasknewsapp.utilities.addAnimTextView
import com.karthik.zohotasknewsapp.viewmodels.BaseViewModel
import com.karthik.zohotasknewsapp.viewmodels.NewsViewModel
import com.karthik.zohotasknewsapp.views.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
class NewsListActivity : BaseActivity(), OnReadMoreListener {

    val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        val view = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(view.root)

        view.tvTemp.addAnimTextView(
            WEATHER_ANIM_DURATION.toLong(),
            R.style.myWeatherTemperatureTextStyle
        )
        view.tvWeatherCondition.addAnimTextView(
            WEATHER_ANIM_DURATION.toLong(),
            R.style.myWeatherConditionTextStyle
        )

        viewModel.WeatherResponse.observe(this) {
            view.tvTemp.setText((it.main.temp.roundToInt() - KELVIN_TO_CELCIUS_VALUE).toString() + CELCIUS_SYMBOL)
            view.tvWeatherCondition.setText(it.weather[0].main
            )
        }

        val newsAdapter = NewsListAdapter(null, this)
        view.rvNewsList.adapter = newsAdapter

        viewModel.filteredList.observe(this) {
            newsAdapter.updateData(it)
        }

        viewModel.WeatherResponse.observe(this) {
            view.tvTemp.setText((it.main.temp.roundToInt() - KELVIN_TO_CELCIUS_VALUE).toString() + CELCIUS_SYMBOL)
            view.tvWeatherCondition.setText(it.weather[0].main)
        }

        viewModel.noDataMsg.observe(this) {
            if (it) {
                view.noNewsMsgNewsList.visibility = View.VISIBLE
            } else {
                view.noNewsMsgNewsList.visibility = View.GONE
            }
        }

        view.svNewsList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterNews(newText)
                return false
            }
        })

        if (isLocationPermissionGranted()) {
            requestWeatherInfo()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQ_CODE
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun requestWeatherInfo(): Unit {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    viewModel.getWeatherInfo(location.latitude, location.longitude)
                }
            }
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onNewsClick(news: News) {
        OnCustomChromeOpen(news.url)
    }

    override fun onReadMoreClick(news: News) {
        OnCustomChromeOpen(news.readMoreUrl)
    }

    private fun OnCustomChromeOpen(url: String) {
        val customIntent = CustomTabsIntent.Builder().build()
        customIntent.intent.setPackage("com.android.chrome")
        customIntent.launchUrl(this, Uri.parse(url))
    }

    private fun isLocationPermissionGranted(): Boolean {
        return !(ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    requestWeatherInfo()
                }
                return
            }
        }
    }

    companion object {
        const val LOCATION_PERMISSION_REQ_CODE = 101
        const val WEATHER_ANIM_DURATION = 500
        const val KELVIN_TO_CELCIUS_VALUE = 273
        const val CELCIUS_SYMBOL = "°C"
    }
}

interface OnReadMoreListener {
    fun onNewsClick(news: News)
    fun onReadMoreClick(news: News)
}