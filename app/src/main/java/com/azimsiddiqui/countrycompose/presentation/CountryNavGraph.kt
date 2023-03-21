package com.azimsiddiqui.countrycompose.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun CountryNavGraph(startDestination: String, viewModelCompose: CountryViewModelCompose) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = ScreenRoute.Home.route) {
            HomeScreen(viewModel = viewModelCompose, navController = navController) { country ->
                viewModelCompose.getCityList(country)
            }
        }
        composable(route = ScreenRoute.City.route) {
            CityComposable(viewModelCompose)
        }
    }
}

@Composable
fun CityComposable(viewModelCompose: CountryViewModelCompose) {

    val countryState = viewModelCompose.cityListState

    if (countryState.cityList != null) {
        LazyColumn {
            items(items = countryState.cityList) { item ->
                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .background(Color.Green)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    elevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Blue.copy(0.5f)
                                    )
                                )
                            )
                            .padding(10.dp),

                        ) {
                        Text(text = item, fontSize = 20.sp, color = Color.White)
                    }
                }
            }
        }
    }

}


enum class ScreenRoute(val route: String) {
    Home("home"),
    City("city")
}