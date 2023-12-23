package com.labinot.bajrami.weatherapp_jpcompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.labinot.bajrami.weatherapp_jpcompose.components.WeatherAppBar
import com.labinot.bajrami.weatherapp_jpcompose.ui.theme.ubuntuFont

@Composable
fun AboutScreen(navController: NavController){

    Scaffold (

        topBar = {
            WeatherAppBar(title = "About",
                         isMainScreen = false,
                         navBackClicked = {

                             navController.popBackStack()
                         })
        },
        containerColor = Color.Transparent,
        content = { mPadding->

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(mPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Text(text = "Weather App v.1.0",
                    color = Color.White,
                    fontFamily = ubuntuFont,
                    fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Weather Api by: https://openweathermap.org",
                    color = Color.White,
                    fontFamily = ubuntuFont,
                    fontWeight = FontWeight.Normal)


            }

        }
    )


}