package com.labinot.bajrami.weatherapp_jpcompose.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.labinot.bajrami.weatherapp_jpcompose.R
import com.labinot.bajrami.weatherapp_jpcompose.navigations.WeatherScreens
import com.labinot.bajrami.weatherapp_jpcompose.utils.formatDate
import com.labinot.bajrami.weatherapp_jpcompose.utils.formatDateTime
import com.labinot.bajrami.weathertest.model.dailyforecastmodel.Daily
import com.labinot.bajrami.weathertest.model.dailyforecastmodel.DailyObject
import com.labinot.bajrami.weathertest.model.dailyforecastmodel.Hourly

@Composable
fun SetIcons(iconCode: String,
             modifier: Modifier) {

    Image(
        modifier = modifier,
        painter = when (iconCode) {
            "01d" -> painterResource(R.drawable.sunny)
            "02d" -> painterResource(R.drawable.partly_cloud)
            "03d" -> painterResource(R.drawable.cloudy)
            "04d" -> painterResource(R.drawable.overcast)
            "09d" -> painterResource(R.drawable.showers)
            "10d" -> painterResource(R.drawable.rain)
            "11d" -> painterResource(R.drawable.storm)
            "13d" -> painterResource(R.drawable.snow)
            "50d" -> painterResource(R.drawable.fog)

            "01n" -> painterResource(R.drawable.night_clear)
            "02n" -> painterResource(R.drawable.nparty_cloud)
            "03n" -> painterResource(R.drawable.ncloudy)
            "04n" -> painterResource(R.drawable.overcast)
            "09n" -> painterResource(R.drawable.nparty_cloud)
            "10n" -> painterResource(R.drawable.nmostly_cloudy_shoewe)
            "11n" -> painterResource(R.drawable.nmostly_cloudy_storm)
            "13n" -> painterResource(R.drawable.nmostly_cloudy_snow)
            else ->
               painterResource(R.drawable.windy)

        },
        contentDescription = stringResource(R.string.icon_image)
    )


}


@Composable
fun HourlyView(timeOb: DailyObject
) {

    AnimatedVisibility(visible = true,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )) {

        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
            contentPadding = PaddingValues(1.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){

            items(items = timeOb.hourly.drop(1)){ item: Hourly ->

                HourlyColumn(hourlyOb = item)

            }

        }

    }

}

@Composable
fun HourlyColumn(hourlyOb: Hourly) {

    Column(modifier = Modifier
        .size(100.dp, 140.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.secondary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

     Text(text = formatDateTime(hourlyOb.dt),
         color = Color.Gray)
        
        Spacer(modifier = Modifier.height(8.dp))


        
        SetIcons(iconCode = hourlyOb.weather[0].icon,
                 modifier = Modifier.size(70.dp))
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(text = "${hourlyOb.temp.toInt()}" + "°",
            color = Color.White)


    }


}

@Composable
fun DailyView(timeOb: DailyObject
              ) {

    AnimatedVisibility(visible = true,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )) {

        LazyColumn(modifier = Modifier
            .fillMaxSize(),
            contentPadding = PaddingValues(1.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){

            items(items = timeOb.daily.drop(1)){ item:Daily ->

                DailyColumn(DailyOb = item)
            }


        }

    }


}

@Composable
fun DailyColumn(DailyOb: Daily) {


        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {


            Text(modifier = Modifier.padding(start = 9.dp),
                text = formatDate(DailyOb.dt)
                    .split(",")[0],
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Row {

                Icon(imageVector = Icons.Sharp.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color(0xffff5353)
                )

                Text(
                    text = "${DailyOb.temp.min.toInt()}" + "°",
                    color = Color.White)

                Spacer(modifier = Modifier.width(6.dp))

                Icon(imageVector = Icons.Sharp.KeyboardArrowUp,
                    contentDescription = null,
                    tint = Color(0xff2eff8c)
                )

                Text(text = "${DailyOb.temp.max.toInt()}" + "°",
                    color = Color.White)
            }

            SetIcons(iconCode = DailyOb.weather[0].icon,
                modifier = Modifier
                    .size(70.dp)
                    .padding(end = 7.dp))


        }





}

@Composable
fun CommonTextField(valueState: MutableState<String>,
                    keyboardType: KeyboardType = KeyboardType.Text,
                    imeAction: ImeAction = ImeAction.Next,
                    onAction: KeyboardActions = KeyboardActions.Default) {

    val focusRequester = remember {

        FocusRequester()
    }

    LaunchedEffect(key1 = Unit){

        focusRequester.requestFocus()
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .height(55.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center) {

        TextField(
            value = valueState.value,
            onValueChange = {valueState.value = it},
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType,
                imeAction = imeAction),
            keyboardActions = onAction,
            colors = TextFieldDefaults.colors(

                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent

            ),
            placeholder = {

                Text(text = stringResource(R.string.search_your_city))

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .focusRequester(focusRequester)

        )

    }

}

@Composable
fun ShowSettingsDropDownMenu(showMenu: MutableState<Boolean>,
                             navController: NavHostController
) {

    val items = listOf("About", "Favorites", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {

        DropdownMenu(
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value = false },
            modifier = Modifier
                .width(140.dp)
                .background(MaterialTheme.colorScheme.secondary)
        ) {

            items.forEachIndexed { index, text ->

                DropdownMenuItem(text = {
                    Text(text = text,
                        color = Color.White,
                        fontWeight = FontWeight.W300,
                    )
                },
                    onClick = {

                        navController.navigate(
                            when(text){

                                "About" -> WeatherScreens.AboutScreen.name
                                "Favorites" -> WeatherScreens.FavoriteScreen.name
                                else -> WeatherScreens.SettingsScreen.name

                            }

                        )

                        showMenu.value = false

                    },

                    leadingIcon = {

                        Icon(imageVector = when(text){

                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.Favorite
                            else -> Icons.Default.Settings

                        },
                            contentDescription = stringResource(R.string.drop_down_icons),
                            tint = Color.White
                        )


                    }

                )


            }

        }

    }

}
