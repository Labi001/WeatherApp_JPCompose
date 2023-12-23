package com.labinot.bajrami.weatherapp_jpcompose.screens.mainscreen


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.labinot.bajrami.weatherapp_jpcompose.R
import com.labinot.bajrami.weatherapp_jpcompose.components.DailyView
import com.labinot.bajrami.weatherapp_jpcompose.components.HourlyView
import com.labinot.bajrami.weatherapp_jpcompose.components.SetIcons
import com.labinot.bajrami.weatherapp_jpcompose.components.ShowSettingsDropDownMenu
import com.labinot.bajrami.weatherapp_jpcompose.navigations.WeatherScreens
import com.labinot.bajrami.weatherapp_jpcompose.screens.favorites.FavoriteViewModel
import com.labinot.bajrami.weatherapp_jpcompose.ui.theme.russoFont
import com.labinot.bajrami.weatherapp_jpcompose.ui.theme.ubuntuFont
import com.labinot.bajrami.weatherapp_jpcompose.utils.Constants
import com.labinot.bajrami.weatherapp_jpcompose.utils.DataStoreRepository
import com.labinot.bajrami.weatherapp_jpcompose.utils.capitalizeFirstLetter
import com.labinot.bajrami.weatherapp_jpcompose.utils.formatDateTime
import com.labinot.bajrami.weatherapp_jpcompose.utils.formatDistanceInKilometers
import com.labinot.bajrami.weathertest.data.DataOrException
import com.labinot.bajrami.weathertest.model.Favorites
import com.labinot.bajrami.weathertest.model.curretWmodel.WeatherObject
import com.labinot.bajrami.weathertest.model.dailyforecastmodel.DailyObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun MainScree(
    navController: NavHostController,
    mainViewModel: MainScreenViewModel,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val dataStore = DataStoreRepository(context)

    val expanded = remember {

        mutableStateOf(false)
    }



    if (expanded.value) {

        ShowSettingsDropDownMenu(
            showMenu = expanded,
            navController = navController
        )

    }

    // val cityFlow: Flow<String> = dataStore.getData("Kichevo")

    // val cityG by cityFlow.collectAsState(initial = "")


//    LaunchedEffect(cityG) {
//
//        if(cityG.isNotEmpty()){
//            mainViewModel.getWeatherData(cityG)
//        }

//    }


    val weatherData = produceState<DataOrException<WeatherObject, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {



        value =
            mainViewModel.getWeatherData(city = dataStore.getData(Constants.CITY_KEY, "Kichevo"),
                unit = dataStore.getUnit(Constants.UNIT_KEY,"metric"))
        // Log.d("DataStore", "City Name: $cityG")


    }.value


    AnimatedVisibility(visible = weatherData.loading == true) {
        Loading()
    }

    if (weatherData.data != null) {
        MainScaffold(
            weatherD = weatherData.data!!,
            modelView = mainViewModel,
            favViewModel = favoriteViewModel,
            onSearchClicked = {

                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            onMoreClicked = {

                expanded.value = !expanded.value
            },

            onFavoriteClicked = { city, country,isAdded ->

                if(isAdded.value){
                    favoriteViewModel.insertFavorite(

                        Favorites(
                            city = city,
                            country = country
                        )

                    )

                }else{

                    favoriteViewModel.deleteFavorite(

                        Favorites(
                            city = city,
                            country = country
                        )

                    )


                }


                showToast(context = context,isAdded)


            }

        )
    }

    AnimatedVisibility(visible = weatherData.e != null) {
        Text(
            text = "Error: ${weatherData.e!!.message}!",
            color = Color.Red
        )
    }


}

fun showToast(context: Context
               , added: MutableState<Boolean>) {

    if(added.value){

        Toast.makeText(context,"Added to Favorites!",
            Toast.LENGTH_SHORT).show()

    }else{

        Toast.makeText(context,"Deleted from Favorites!",
            Toast.LENGTH_SHORT).show()

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScaffold(
    weatherD: WeatherObject,
    modelView: MainScreenViewModel,
    onSearchClicked: () -> Unit = {},
    onMoreClicked: () -> Unit = {},
    onFavoriteClicked: (String, String,MutableState<Boolean>) ->
    Unit = { city: String, country: String,isAdded:MutableState<Boolean> -> },
    favViewModel: FavoriteViewModel,
) {



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        val alreadyFavorite = favViewModel.favList
            .collectAsState().value.filter { item ->

                (item.city == weatherD.name)

            }

        val isAdded = remember {

            mutableStateOf(false)
        }


        Row(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            IconButton(onClick = {

                if(alreadyFavorite.isNotEmpty()){
                    isAdded.value = false
                }else {

                    isAdded.value = true
                }

                onFavoriteClicked(weatherD.name, weatherD.sys.country,isAdded)

            }) {

                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(top = 5.dp),
                    imageVector = if (alreadyFavorite.isEmpty()) Icons.Outlined.FavoriteBorder
                    else Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.location_icon),
                    tint = if (alreadyFavorite.isEmpty()) Color.White
                    else Color.Green
                )

            }




            Column(modifier = Modifier.weight(0.8f)) {

                Text(
                    text = weatherD.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )

                Text(
                    text = weatherD.sys.country,
                    color = Color.Gray,

                    )

            }

            IconButton(
                onClick = { onSearchClicked.invoke() }) {

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon),
                    tint = Color.White
                )

            }

            IconButton(
                onClick = { onMoreClicked.invoke() }) {

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.more_icon),
                    tint = Color.White
                )

            }


        }
        Spacer(modifier = Modifier.height(5.dp))

        MainContent(
            weather = weatherD,
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp),
            text = stringResource(R.string.hourly_forecast),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(8.dp))

        HourlyContent(
            weatherOb = weatherD,
            mViewModel = modelView
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp),
            text = stringResource(R.string.daily_forecast),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(10.dp))

        DailyContent(
            weatherOb = weatherD,
            mViewModel = modelView
        )


    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainContent(
    weather: WeatherObject,
) {

    val context = LocalContext.current
    val dataStore = DataStoreRepository(context)

    var expanded by remember {
        mutableStateOf(false)
    }

    val isMetric = remember {

        mutableStateOf(dataStore.getUnit(
            Constants.UNIT_KEY,"metric") == "metric")
    }

    val currentDateTime = LocalDateTime.now()

    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    val currentDateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())

    val currentTime = currentDateTime.format(timeFormatter)
    val currentDate = currentDateTime.format(currentDateFormatter)


    AnimatedVisibility(visible = true) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalAlignment = Alignment.Top
            ) {

                Column(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {

                    Text(
                        text = "Current Weather",
                        color = Color.White,
                        fontFamily = ubuntuFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )

                    SetIcons(
                        iconCode = weather.weather[0].icon,
                        modifier = Modifier
                            .height(90.dp)
                            .width(150.dp)
                    )

                    Text(
                        text = currentDate,
                        color = Color.Gray,
                        fontFamily = ubuntuFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )

                    Text(
                        buildAnnotatedString {

                            withStyle(
                                style = SpanStyle(
                                    color = Color.LightGray,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            ) {

                                append("Last Check: ")
                            }

                            withStyle(
                                style = SpanStyle(
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                            ) {

                                append(currentTime)
                            }

                        }
                    )

                    AnimatedVisibility(visible = expanded) {

                        Column() {

                            Text(buildAnnotatedString {

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.LightGray,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                ) {

                                    append("Humidity: ")
                                }

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp
                                    )
                                ) {

                                    append("${weather.main.humidity} %")
                                }

                            })

                            Text(buildAnnotatedString {

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.LightGray,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                ) {

                                    append("Pressure: ")
                                }

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp
                                    )
                                ) {

                                    append("${weather.main.pressure} hPa")
                                }

                            })

                            Text(buildAnnotatedString {

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.LightGray,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                ) {

                                    append("Wind Speed: ")
                                }

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp
                                    )
                                ) {

                                    append(if(isMetric.value)"${weather.wind.speed} m/s"
                                    else "${weather.wind.speed} mph")
                                }

                            })


                        }


                    }


                }


                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Top
                ) {


                    Text(
                        text = if(isMetric.value) "${weather.main.temp.toInt()}°C" else "${weather.main.temp.toInt()}°F",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp,
                        fontFamily = russoFont
                    )

                    Spacer(modifier = Modifier.height(1.dp))

                    Text(
                        buildAnnotatedString {

                            withStyle(
                                style = SpanStyle(
                                    color = Color.LightGray,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            ) {

                                append(stringResource(R.string.feels_like))
                            }

                            withStyle(
                                style = SpanStyle(
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                            ) {

                                append(if(isMetric.value)"${weather.main.feels_like.toInt()}°C"
                                else "${weather.main.feels_like.toInt()}°F" )
                            }

                        }

                    )


                    Text(modifier = Modifier.fillMaxWidth(),
                        text = capitalizeFirstLetter(weather.weather[0].description),
                        color = Color.LightGray,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    AnimatedVisibility(visible = expanded) {

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {

                            Text(buildAnnotatedString {

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.LightGray,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                ) {

                                    append(stringResource(R.string.sunrise))
                                }

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp
                                    )
                                ) {

                                    append(formatDateTime(weather.sys.sunrise))
                                }

                            })

                            Text(buildAnnotatedString {

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.LightGray,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                ) {

                                    append(stringResource(R.string.sunset))
                                }

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp
                                    )
                                ) {

                                    append(formatDateTime(weather.sys.sunset))
                                }

                            })
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(buildAnnotatedString {

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.LightGray,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                ) {

                                    append("Visibility: ")
                                }

                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp
                                    )
                                ) {

                                    append("${formatDistanceInKilometers(weather.visibility)} km")
                                }

                            })
                        }


                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {

                        Image(
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {

                                    expanded = !expanded
                                },
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp
                            else Icons.Default.KeyboardArrowDown,
                            contentDescription = "Arrow ",
                            colorFilter = ColorFilter.tint(
                                color = Color.White
                            )
                        )

                    }


                }

            }


        }
    }


}

@Composable
fun HourlyContent(
    weatherOb: WeatherObject,
    mViewModel: MainScreenViewModel
) {

    val mLatitude: String = weatherOb.coord.lat.toString()
    val mLongitude: String = weatherOb.coord.lon.toString()

    val context = LocalContext.current
    val dataStore = DataStoreRepository(context)


    val timeWeather = produceState<DataOrException<DailyObject, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {

        value = mViewModel.getDailyWeatherD(latitude = mLatitude, longitude = mLongitude,
            unit = dataStore.getUnit(Constants.UNIT_KEY,"metric"))

    }.value

    AnimatedVisibility(visible = timeWeather.loading == true) {
        Loading()
    }

    if (timeWeather.data != null) {
        HourlyView(
            timeOb = timeWeather.data!!
        )
    }

    AnimatedVisibility(visible = timeWeather.e != null) {
        Text(
            text = "Error: ${timeWeather.e!!.message}!",
            color = Color.Red
        )
    }


}

@Composable
fun DailyContent(
    weatherOb: WeatherObject,
    mViewModel: MainScreenViewModel
) {

    val context = LocalContext.current
    val dataStore = DataStoreRepository(context)

    val mLatitude: String = weatherOb.coord.lat.toString()
    val mLongitude: String = weatherOb.coord.lon.toString()

    val timeWeather = produceState<DataOrException<DailyObject, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {

        value = mViewModel.getDailyWeatherD(latitude = mLatitude, longitude = mLongitude,
            unit = dataStore.getUnit(Constants.UNIT_KEY,"metric"))

    }.value

    AnimatedVisibility(visible = timeWeather.loading == true,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )) {
        Loading()
    }

    if (timeWeather.data != null) {
        DailyView(
            timeOb = timeWeather.data!!,
        )
    }

    AnimatedVisibility(visible = timeWeather.e != null) {
        Text(
            text = "Error: ${timeWeather.e!!.message}!",
            color = Color.Red
        )
    }

}


@Composable
fun Loading() {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

        CircularProgressIndicator(color = Color.White)
    }

}