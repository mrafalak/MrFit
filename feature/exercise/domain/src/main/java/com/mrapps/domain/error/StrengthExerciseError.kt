package com.mrapps.domain.error

import com.mrapps.domain.Error

sealed interface StrengthExerciseError : Error {

    enum class MovementType : StrengthExerciseError {
        NOT_SELECTED
    }

    enum class MuscleGroup : StrengthExerciseError {
        NOT_SELECTED
    }

    enum class ExerciseGoal : StrengthExerciseError {
        NOT_SELECTED
    }
}