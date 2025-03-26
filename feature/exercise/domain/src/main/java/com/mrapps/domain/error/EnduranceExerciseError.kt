package com.mrapps.domain.error

import com.mrapps.domain.Error

sealed interface EnduranceExerciseError : Error {

    enum class ActivityType : EnduranceExerciseError {
        NOT_SELECTED
    }

    enum class DurationUnit : EnduranceExerciseError {
        NOT_SELECTED
    }
}