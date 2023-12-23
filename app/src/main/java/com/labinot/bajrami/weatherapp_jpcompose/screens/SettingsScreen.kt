package com.labinot.bajrami.weatherapp_jpcompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.labinot.bajrami.weatherapp_jpcompose.R
import com.labinot.bajrami.weatherapp_jpcompose.components.WeatherAppBar
import com.labinot.bajrami.weatherapp_jpcompose.navigations.WeatherScreens
import com.labinot.bajrami.weatherapp_jpcompose.utils.Constants
import com.labinot.bajrami.weatherapp_jpcompose.utils.DataStoreRepository

@Composable
fun SettingsScreen(navController: NavController){

    val context = LocalContext.current
    val dataStore = DataStoreRepository(context)

    val isMetric = remember {

        mutableStateOf(dataStore.getUnit(
            Constants.UNIT_KEY,"metric") == "metric")
    }

    Scaffold(

        topBar = {

            WeatherAppBar(
                isMainScreen = false,
                title = "Settings Screen",
                navBackClicked = {

                    navController.popBackStack()
                })

        },
        containerColor = Color.Transparent,
        content = {mPadding ->

            Column(modifier = Modifier
                .padding(mPadding)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)) {

                    Button(onClick ={

                                    dataStore.saveData(Constants.UNIT_KEY,"imperial")
                        navController.navigate(WeatherScreens.MainScreen.name)

                    },
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth()
                            .weight(0.50f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(

                            containerColor = if(isMetric.value) MaterialTheme.colorScheme.secondary
                            else Color.Green

                        )

                    ) {

                        Text(text = stringResource(R.string.imperial),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White)

                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    Button(onClick ={

                        dataStore.saveData(Constants.UNIT_KEY,"metric")
                        navController.navigate(WeatherScreens.MainScreen.name)
                    },
                        modifier = Modifier
                            .height(60.dp)
                            .fillMaxWidth()
                            .weight(0.50f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(


                            containerColor = if(isMetric.value) Color.Green
                            else MaterialTheme.colorScheme.secondary

                        )

                    ) {

                        Text(text = stringResource(R.string.metric),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White)

                    }

                }



            }

        }

    )


}