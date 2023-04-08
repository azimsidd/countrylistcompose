package com.azimsiddiqui.countrycompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azimsiddiqui.countrycompose.domain.entity.CountryDetail
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

    var countryState by mutableStateOf(CountryState())

    val originalCountryList = mutableStateOf(emptyList<String>())

    private var countryDataList = listOf<CountryDetail>()

    init {
        getCountryList()
    }

    fun getCountryList() {
        getCountryDataUseCase.invoke().onEach {
            when (it) {
                is ApiResult.Loading -> {
                    countryState = CountryState(isLoading = true)
                }
                is ApiResult.Success -> {
                    countryDataList = it.data.orEmpty()
                    val countryList = countryDataList.map { it.country }
                    originalCountryList.value = countryList
                    countryState = CountryState(isLoading = false, countryList = countryList)
                }
                is ApiResult.Error -> {
                    countryState =
                        CountryState(isLoading = false, errorMessage = it.message.orEmpty())
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onSearchQueryChanged(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalCountryList.value
        } else {
            originalCountryList.value.filter { country ->
                country.contains(query, true)
            }
        }
        countryState = countryState.copy(countryList = filteredList)
    }


    //fetch the all cities
    fun getCityList(country: String) {
        viewModelScope.launch {
            // get list of city name based on country
            val cityList = countryDataList.firstOrNull { it.country == country }?.cities.orEmpty()
            countryState = countryState.copy(cityList = cityList)
        }
    }

}