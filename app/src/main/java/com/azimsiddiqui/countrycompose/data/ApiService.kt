package com.azimsiddiqui.countrycompose.data

import com.azimsiddiqui.countrycompose.data.model.CountryDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("countries/")
    suspend fun getCountries():Response<CountryDto>
}