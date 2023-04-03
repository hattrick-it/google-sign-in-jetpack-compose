package com.hattrick.domain.resource

sealed class DataResult<out T> {

    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val error: CustomError) : DataResult<Nothing>()

    suspend fun checkResult(
        onSuccess: suspend (T) -> Unit,
        onError: suspend (CustomError) -> Unit
    ) {
        when (this) {
            is Success -> {
                onSuccess(data)
            }
            is Error -> {
                onError(error)
            }
        }
    }

    suspend fun <V> checkResultAndReturn(
        onSuccess: suspend (T) -> V,
        onError: suspend (CustomError) -> V
    ): V {
        return when (this) {
            is Success -> {
                onSuccess(data)
            }
            is Error -> {
                onError(error)
            }
        }
    }
}
