package com.karthik.zohotasknewsapp.dependencyInjection

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.karthik.zohotasknewsapp.api.NewsApi
import com.karthik.zohotasknewsapp.api.WeatherApi
import com.karthik.zohotasknewsapp.database.AppDatabase
import com.karthik.zohotasknewsapp.utilities.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient
                .Builder()
                .build()
        }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideNewsApiService(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)

    @Provides
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.newsDao()
}