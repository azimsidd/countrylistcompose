package com.azimsiddiqui.countrycompose.domain.repository

import com.azimsiddiqui.countrycompose.domain.entity.CountryDetail

interface CountryRepository {
    suspend fun getCountryList(): List<CountryDetail>
    suspend fun getCityList(countryName:String):List<String>
}