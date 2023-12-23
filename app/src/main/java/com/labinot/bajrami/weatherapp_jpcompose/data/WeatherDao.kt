package com.labinot.bajrami.weathertest.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.labinot.bajrami.weathertest.model.Favorites
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * from fav_tbl")
    fun getFavorites(): Flow<List<Favorites>>

    @Query("SELECT * from fav_tbl where city =:city")
    suspend fun getFavById(city:String): Favorites

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorites: Favorites)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatesFavorite(favorites: Favorites)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFavorite()

    @Delete
    suspend fun deleteFavorite(favorites: Favorites)

}