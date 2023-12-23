package com.labinot.bajrami.weathertest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.labinot.bajrami.weathertest.model.Favorites

@Database(entities = [Favorites::class], version = 1, exportSchema = false)
abstract class WeatherDataBase: RoomDatabase() {

    abstract fun weatherDao():WeatherDao

}
