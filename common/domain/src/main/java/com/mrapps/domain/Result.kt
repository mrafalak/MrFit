package com.mrapps.domain

typealias RootError = Error

sealed interface Result<out D, out E : RootError> {

    data class Success<out D, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out D, out E : RootError>(val error: E) : Result<D, E>

    fun <R> map(transform: (D) -> R): Result<R, E> = when (this) {
        is Success -> Success(transform(data))
        is Error -> Error(error)
    }

    fun getOrNull(): D? = when (this) {
        is Success -> data
        is Error -> null
    }

    fun ifSuccess(action: (D) -> Unit): Result<D, E> = apply {
        if (this is Success) action(data)
    }

    fun ifError(action: (E) -> Unit): Result<D, E> = apply {
        if (this is Error) action(error)
    }

    fun <R> fold(
        onSuccess: (D) -> R,
        onError: (E) -> R
    ): R = when (this) {
        is Success -> onSuccess(data)
        is Error -> onError(error)
    }
}