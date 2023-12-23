package com.labinot.bajrami.weatherapp_jpcompose.screens.mainscreen


import androidx.lifecycle.ViewModel
import com.labinot.bajrami.weathertest.data.DataOrException
import com.labinot.bajrami.weathertest.model.curretWmodel.WeatherObject
import com.labinot.bajrami.weathertest.model.dailyforecastmodel.DailyObject
import com.labinot.bajrami.weathertest.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {


    suspend fun getWeatherData(city:String,unit:String):
    DataOrException<WeatherObject,Boolean,Exception>{

       return repository.getWeather(cityQuery = city, unit = unit)
    }



    suspend fun getDailyWeatherD(latitude:String,longitude:String,unit: String):
            DataOrException<DailyObject,Boolean,Exception>{

        return repository.getDailyWeather(lat = latitude, lon = longitude, unit = unit)
    }

}