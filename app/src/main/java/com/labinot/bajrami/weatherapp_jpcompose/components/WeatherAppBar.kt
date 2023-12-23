package com.labinot.bajrami.weatherapp_jpcompose.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.labinot.bajrami.weatherapp_jpcompose.R
import com.labinot.bajrami.weatherapp_jpcompose.ui.theme.ubuntuFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(

    title: String = "title",
    isMainScreen: Boolean = true,
    navBackClicked: () -> Unit = {},

){


    TopAppBar(title = {

                      Text(text = title,
                          color = Color.White,
                          fontFamily = ubuntuFont)

    },

            navigationIcon = {

                             IconButton(onClick = { navBackClicked.invoke() }) {

                                 Icon(imageVector = Icons.Default.ArrowBack,
                                     contentDescription = stringResource(R.string.icon_back),
                                     tint = Color.White
                                 )
                                 
                             }


            },
        colors = topAppBarColors(
            containerColor = Color.Transparent
        ),

        actions = {

            if(isMainScreen){

                IconButton(onClick = {  }) {

                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_icon),
                        tint = Color.White)

                }



            }


        }



       )



}