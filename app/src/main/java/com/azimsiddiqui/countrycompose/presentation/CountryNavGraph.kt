package com.azimsiddiqui.countrycompose.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun CountryNavGraph(startDestination: String, viewModelCompose: CountryViewModelCompose) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = ScreenRoute.Home.route) {
            HomeScreen(viewModel = viewModelCompose) { country ->
                navController.navigate("city")
                viewModelCompose.getCityList(country)
            }
        }
        composable(route = ScreenRoute.City.route) {
            CityComposable(viewModelCompose, onBackPress = {
                navController.popBackStack()
            }) {
                navController.navigate("sheet")
            }
        }
        composable(route = ScreenRoute.Sheet.route) {
            BottomSheet()
        }
    }
}


enum class ScreenRoute(val route: String) {
    Home("home"),
    City("city"),
    Sheet("sheet"),
}