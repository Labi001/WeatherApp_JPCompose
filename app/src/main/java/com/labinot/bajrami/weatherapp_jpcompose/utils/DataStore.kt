package com.labinot.bajrami.weatherapp_jpcompose.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.map



class DataStoreRepository (private val context: Context) {

//    companion object {
//
//        private val Context.dataStore:DataStore<Preferences> by preferencesDataStore(PREFERENCE_NAME)
//        val CITY_KEY = stringPreferencesKey(Constants.CITY_KEY)
//
//    }
//
//
//
//    fun getData( defaultValue: String): Flow<String> {
//
//        return context.dataStore.data.map { preferences ->
//            preferences[CITY_KEY] ?: defaultValue
//        }
//    }
//
//    suspend fun saveCity(name:String) {
//
//        context.dataStore.edit { preferences ->
//
//            preferences[CITY_KEY] = name
//        }
//
//    }


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun saveUnit(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getUnit(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }




}