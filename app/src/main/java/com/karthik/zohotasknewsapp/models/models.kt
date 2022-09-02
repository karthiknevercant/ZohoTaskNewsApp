package com.karthik.zohotasknewsapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NewsTable")
data class News(
    @PrimaryKey
    val id: String,
    val author: String,
    val content: String,
    val date: String,
    val imageUrl: String,
    val readMoreUrl: String,
    val time: String,
    val title: String,
    val url: String
)

data class NewsResponse(
    val category: String,
    val data: List<News>,
    val success: Boolean
)

data class Clouds(
    val all: Int
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)


data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int,
    val grnd_level: Int
)

data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)