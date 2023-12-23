package com.labinot.bajrami.weatherapp_jpcompose.navigations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.labinot.bajrami.weatherapp_jpcompose.screens.AboutScreen
import com.labinot.bajrami.weatherapp_jpcompose.screens.favorites.FavoriteScreen
import com.labinot.bajrami.weatherapp_jpcompose.screens.mainscreen.MainScree
import com.labinot.bajrami.weatherapp_jpcompose.screens.SearchScreen
import com.labinot.bajrami.weatherapp_jpcompose.screens.SettingsScreen
import com.labinot.bajrami.weatherapp_jpcompose.screens.mainscreen.MainScreenViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherNavigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WeatherScreens.MainScreen.name ){

        composable(WeatherScreens.MainScreen.name){

            val mainViewModel = hiltViewModel<MainScreenViewModel>()
            MainScree(navController = navController,
                     mainViewModel)
            
        }

        composable(WeatherScreens.SearchScreen.name,
          enterTransition = {
                slideInHorizontally (
                    initialOffsetX = {fullWidth -> -fullWidth},
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
          },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )

            }
            ){

            SearchScreen(navController = navController)

        }

        composable(WeatherScreens.FavoriteScreen.name,
            enterTransition = {
                slideInHorizontally (
                    initialOffsetX = {fullWidth -> fullWidth},
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )

            }){

            FavoriteScreen(navController = navController)

        }

        composable(WeatherScreens.SettingsScreen.name,
            enterTransition = {
                slideInHorizontally (
                    initialOffsetX = {fullWidth -> fullWidth},
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )

            }){

            SettingsScreen(navController = navController)

        }

        composable(WeatherScreens.AboutScreen.name,
            enterTransition = {
                slideInHorizontally (
                    initialOffsetX = {fullWidth -> fullWidth},
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )

            }){

            AboutScreen(navController = navController)

        }





    }


}