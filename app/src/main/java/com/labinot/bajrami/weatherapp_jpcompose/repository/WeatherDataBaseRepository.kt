package com.labinot.bajrami.weathertest.repository

import com.labinot.bajrami.weathertest.data.WeatherDao
import com.labinot.bajrami.weathertest.model.Favorites
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDataBaseRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorites>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorites: Favorites) = weatherDao.insertFavorite(favorites)
    suspend fun deleteFavorite(favorites: Favorites) = weatherDao.deleteFavorite(favorites)

}