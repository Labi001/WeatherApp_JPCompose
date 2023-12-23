package com.labinot.bajrami.weatherapp_jpcompose.screens



import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import com.labinot.bajrami.weatherapp_jpcompose.components.CommonTextField
import com.labinot.bajrami.weatherapp_jpcompose.components.WeatherAppBar
import com.labinot.bajrami.weatherapp_jpcompose.navigations.WeatherScreens
import com.labinot.bajrami.weatherapp_jpcompose.utils.Constants
import com.labinot.bajrami.weatherapp_jpcompose.utils.DataStoreRepository
import kotlinx.coroutines.launch


@Composable
fun SearchScreen(navController: NavController,
                 ){

    val context = LocalContext.current
    val dataStore = DataStoreRepository(context)
    val scope = rememberCoroutineScope()


    Scaffold (topBar = {

        WeatherAppBar(
             title = "Search Screen",
            isMainScreen = false,
            navBackClicked = {
                navController.popBackStack()
            })
    },
        containerColor = Color.Transparent,
        content = { mpaddingValue ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(mpaddingValue),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Search_Bar() { mCity ->

                    scope.launch {

                        try {
                            dataStore.saveData(Constants.CITY_KEY,mCity)
                        } catch (e: Exception) {

                            e.printStackTrace()
                        }

                    }

                    navController.navigate(WeatherScreens.MainScreen.name)

                }

            }
        }

    )


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Search_Bar(
    onSearch:(String) -> Unit ={}
) {

    val searchQueryState = rememberSaveable {

        mutableStateOf("")
    }

    val valid = remember(searchQueryState.value) {

        searchQueryState.value.trim().isNotEmpty()
    }

    val keyBoardController = LocalSoftwareKeyboardController.current

    CommonTextField(
        valueState = searchQueryState,
        onAction = KeyboardActions {

            if (!valid) return@KeyboardActions

            onSearch(searchQueryState.value.trim())
            searchQueryState.value = ""
            keyBoardController?.hide()


        }
    )
}








