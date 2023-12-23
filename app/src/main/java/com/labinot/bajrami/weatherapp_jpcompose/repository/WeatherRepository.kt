package com.labinot.bajrami.weathertest.repository


import com.labinot.bajrami.weatherapp_jpcompose.network.WeatherApi
import com.labinot.bajrami.weathertest.data.DataOrException
import com.labinot.bajrami.weathertest.model.curretWmodel.WeatherObject
import com.labinot.bajrami.weathertest.model.dailyforecastmodel.DailyObject


import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(cityQuery:String,unit:String): DataOrException<WeatherObject, Boolean, Exception> {

        val response = try {

            api.getWeather(query = cityQuery, units = unit)

        }catch (e:Exception){

            return DataOrException(e=e)
        }
             return DataOrException(data = response)
    }


    suspend fun getDailyWeather(lat:String,lon:String,unit: String): DataOrException<DailyObject,Boolean,Exception>{

        val response = try {

            api.getDailyWeather(lat = lat, lon = lon, units = unit)

        }catch (e:Exception){

            return DataOrException(e=e)
        }
        return DataOrException(data = response)
    }


}