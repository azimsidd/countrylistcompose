package com.azimsiddiqui.countrycompose.data.repository

import com.azimsiddiqui.countrycompose.common.toDomain
import com.azimsiddiqui.countrycompose.data.ApiService
import com.azimsiddiqui.countrycompose.data.model.CountryDetail
import com.azimsiddiqui.countrycompose.util.SafeApiRequest
import com.azimsiddiqui.countrycompose.domain.repository.CountryRepository
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    CountryRepository,
    SafeApiRequest() {
    override suspend fun getCountryList(): List<CountryDetail> {
        val countryList = safeApiRequest {
            apiService.getCountries()
        }.toDomain().countryList
        return countryList
    }

    override suspend fun getCityList(countryName: String): List<String> {
        val cityList = safeApiRequest {
            apiService.getCountries()
        }.toDomain().countryList.find { it.country == countryName }?.cities ?: listOf()
        return cityList
    }
}