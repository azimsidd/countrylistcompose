package com.azimsiddiqui.countrycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.azimsiddiqui.countrycompose.presentation.CountryNavGraph
import com.azimsiddiqui.countrycompose.presentation.CountryViewModelCompose
import com.azimsiddiqui.countrycompose.presentation.ScreenRoute
import com.azimsiddiqui.countrycompose.ui.theme.CountryComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val countryViewModel: CountryViewModelCompose = hiltViewModel()
                    CountryNavGraph(startDestination = ScreenRoute.Home.route, viewModelCompose = countryViewModel)
                }
            }
        }
    }
}
