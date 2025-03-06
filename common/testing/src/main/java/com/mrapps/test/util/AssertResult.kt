package com.mrapps.test.util

import assertk.assertThat
import assertk.assertions.isTrue
import com.mrapps.domain.Error
import com.mrapps.domain.Result

suspend fun <T> assertResultSuccess(
    operation: suspend () -> Result<T, Error>
): T {
    val result = operation()
    assertThat(result is Result.Success).isTrue()
    return (result as Result.Success).data
}

suspend fun <T> assertResultError(
    error: Error,
    operation: suspend () -> Result<T, Error>
) {
    val result = operation()
    assertThat(result is Result.Error).isTrue()
    assertThat((result as Result.Error).error == error).isTrue()
}