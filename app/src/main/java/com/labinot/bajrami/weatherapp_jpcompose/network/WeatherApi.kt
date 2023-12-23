package com.labinot.bajrami.weatherapp_jpcompose.network


import com.labinot.bajrami.weatherapp_jpcompose.utils.Constants
import com.labinot.bajrami.weathertest.model.curretWmodel.WeatherObject
import com.labinot.bajrami.weathertest.model.dailyforecastmodel.DailyObject
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET(value = "data/2.5/weather")
    suspend fun getWeather(

        @Query("q") query:String,
        @Query("units") units: String,
        @Query("lang") lang: String = "en",
        @Query("appid") appid: String = Constants.API_KEY


    ): WeatherObject


    @GET(value = "data/2.5/onecall")
    suspend fun getDailyWeather(

        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("units") units: String,
        @Query("lang") lang: String = "en",
        @Query("appid") appid: String = Constants.API_KEY

    ): DailyObject

}