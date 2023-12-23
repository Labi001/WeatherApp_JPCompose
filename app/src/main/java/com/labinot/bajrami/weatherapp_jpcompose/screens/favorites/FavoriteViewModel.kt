package com.labinot.bajrami.weatherapp_jpcompose.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labinot.bajrami.weathertest.model.Favorites
import com.labinot.bajrami.weathertest.repository.WeatherDataBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDataBaseRepository):ViewModel() {


    private val _favList = MutableStateFlow<List<Favorites>>(emptyList())
    val favList = _favList.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {

            repository.getFavorites().distinctUntilChanged()
                .collect{ listofFaves ->

                    if(listofFaves.isNullOrEmpty()) {

                        Log.d("TAG"," Empty Favs")

                    }else {

                        _favList.value = listofFaves
                        Log.d("TAG",":${favList.value}")

                    }


                }

        }

    }

    fun insertFavorite(favorites: Favorites) = viewModelScope.launch {

        repository.insertFavorite(favorites)
    }


    fun deleteFavorite(favorites: Favorites) = viewModelScope.launch {

        repository.deleteFavorite(favorites)
    }

}