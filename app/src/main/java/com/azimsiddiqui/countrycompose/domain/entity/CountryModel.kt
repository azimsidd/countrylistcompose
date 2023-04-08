package com.azimsiddiqui.countrycompose.domain.entity

data class CountryModel(
    val countryList: List<CountryDetail>,
    val error: Boolean,
    val msg: String
)