package com.azimsiddiqui.countrycompose.domain.usecase

import com.azimsiddiqui.countrycompose.domain.entity.CountryDetail
import com.azimsiddiqui.countrycompose.domain.repository.CountryRepository
import com.azimsiddiqui.countrycompose.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCountryDataUseCase @Inject constructor(private val countryRepository: CountryRepository) {

    operator fun invoke(): Flow<ApiResult<List<CountryDetail>>> {
        return flow {
            emit(ApiResult.Loading(""))
            try {
                emit(ApiResult.Success(countryRepository.getCountryList()))
            } catch (e: Exception) {
                emit(ApiResult.Error(e.message))
            }
        }
    }

}

