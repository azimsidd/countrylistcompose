package com.azimsiddiqui.countrycompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azimsiddiqui.countrycompose.data.model.CountryDetail
import com.azimsiddiqui.countrycompose.domain.usecase.GetCountryDataUseCase
import com.azimsiddiqui.countrycompose.util.ApiResult
import com.azimsiddiqui.countrycompose.util.CountryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModelCompose @Inject constructor(
    private val getCountryDataUseCase: GetCountryDataUseCase
) : ViewModel() {

    var countryListState by mutableStateOf(CountryState())
    var cityListState by mutableStateOf(CountryState())

    val originalCountryList = mutableStateOf(emptyList<String>())

    private var countryDataList = listOf<CountryDetail>()

    init {
        getCountryList()
    }

    fun getCountryList() {
        getCountryDataUseCase.invoke().onEach {
            when (it) {
                is ApiResult.Loading -> {
                    countryListState = CountryState(isLoading = true)
                }
                is ApiResult.Success -> {
                    countryDataList = it.data ?: emptyList()
                    val countryList = countryDataList.map { it.country }
                    originalCountryList.value = countryList
                    countryListState = CountryState(countryList = countryList)
                }
                is ApiResult.Error -> {
                    countryListState = CountryState(errorMessage = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onSearchQueryChanged(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalCountryList.value
        } else {
            val resultList = mutableListOf<String>()
            for (country in originalCountryList.value) {
                if (country.contains(query, true)) {
                    resultList.add(country)
                }
            }
            resultList
        }
        countryListState = countryListState.copy(countryList = filteredList)
    }


    //fetch the all cities
    fun getCityList(country: String) {
        viewModelScope.launch {
            // get list of city name based on country
            val cityList = countryDataList.find { it.country == country }?.cities ?: listOf()
            countryListState = CountryState(cityList = cityList)
        }
    }

}