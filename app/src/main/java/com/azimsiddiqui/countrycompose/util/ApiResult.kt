package com.azimsiddiqui.countrycompose.util

sealed class ApiResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : ApiResult<T>(data = data)
    class Loading<T>(message: String) : ApiResult<T>(message = message)
    class Error<T>(message: String?) : ApiResult<T>(message = message)
}
