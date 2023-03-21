package com.azimsiddiqui.countrycompose.data.model

data class CountryModel(
    val countryList: List<CountryDetail>,
    val error: Boolean,
    val msg: String
)