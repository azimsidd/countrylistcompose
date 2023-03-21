package com.azimsiddiqui.countrycompose.util

data class CountryState(
    val countryList: List<String>? = emptyList(),
    val cityList: List<String>? = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
