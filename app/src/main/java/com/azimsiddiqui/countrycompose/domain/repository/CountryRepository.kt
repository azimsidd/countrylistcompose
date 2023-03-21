package com.azimsiddiqui.countrycompose.domain.repository

import com.azimsiddiqui.countrycompose.data.model.CountryDetail

interface CountryRepository {
    suspend fun getCountryList(): List<CountryDetail>
    suspend fun getCityList(countryName:String):List<String>
}