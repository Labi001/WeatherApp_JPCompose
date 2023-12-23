package com.labinot.bajrami.weatherapp_jpcompose.screens.favorites


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.labinot.bajrami.weatherapp_jpcompose.R
import com.labinot.bajrami.weatherapp_jpcompose.components.WeatherAppBar
import com.labinot.bajrami.weatherapp_jpcompose.navigations.WeatherScreens
import com.labinot.bajrami.weatherapp_jpcompose.utils.Constants
import com.labinot.bajrami.weatherapp_jpcompose.utils.DataStoreRepository
import com.labinot.bajrami.weathertest.model.Favorites

@Composable
fun FavoriteScreen(navController: NavController,
                   favoriteViewModel: FavoriteViewModel = hiltViewModel()
){

    Scaffold (

        topBar = {

          WeatherAppBar(
              title = "Favorite Cities",
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
                    verticalArrangement = Arrangement.Top) {

                    val list = favoriteViewModel.favList.collectAsState().value
                    LazyColumn{

                        items(items = list){ favoritItem ->

                            CityRow(favorite = favoritItem,
                                navController = navController,
                                favoriteViewModel)
                        }

                    }

                }





        }


    )


}

@Composable
fun CityRow(favorite: Favorites,
            navController: NavController,
            favoriteViewModel: FavoriteViewModel) {

    val context = LocalContext.current
    val dataStore = DataStoreRepository(context)


    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(6.dp)
        .clickable {

            dataStore.saveData(Constants.CITY_KEY, favorite.city)
            navController.navigate(WeatherScreens.MainScreen.name) {

                popUpTo(0)
            }
        },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = MaterialTheme.colorScheme.secondary
    ) {

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
            ) {

            Text(text = favorite.city,
                fontWeight = FontWeight.Bold)

            Surface(shape = CircleShape,
                color = MaterialTheme.colorScheme.primary) {

                Text(text = favorite.country,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(2.dp),
                    fontWeight = FontWeight.Bold)

            }

            Icon(imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete_icon),
                tint = Color.Red,
                modifier = Modifier.clickable {
                 favoriteViewModel.deleteFavorite(favorite)
                    Toast.makeText(context,favorite.city + " is Deleted from Favorites",
                        Toast.LENGTH_SHORT).show()
                }
            )



        }

    }

}







