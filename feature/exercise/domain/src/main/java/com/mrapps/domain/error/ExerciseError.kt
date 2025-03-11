package com.mrapps.domain.error

import com.mrapps.domain.Error

sealed interface ExerciseError : Error {

    enum class Name : ExerciseError {
        EMPTY,
        DUPLICATED,
        TOO_LONG,
        UNKNOWN,
    }

    enum class Description : ExerciseError {
        TOO_LONG
    }
}