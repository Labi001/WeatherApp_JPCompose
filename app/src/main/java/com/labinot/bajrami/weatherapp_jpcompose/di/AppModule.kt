package com.labinot.bajrami.weatherapp_jpcompose.di

import android.content.Context
import androidx.room.Room
import com.labinot.bajrami.weatherapp_jpcompose.network.WeatherApi
import com.labinot.bajrami.weatherapp_jpcompose.utils.Constants.BASE_URL
import com.labinot.bajrami.weathertest.data.WeatherDao
import com.labinot.bajrami.weathertest.data.WeatherDataBase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDataBase: WeatherDataBase):WeatherDao
    =weatherDataBase.weatherDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDataBase
    =Room.databaseBuilder(
        context,
        WeatherDataBase::class.java,
        "weather_database"

    )
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }






}