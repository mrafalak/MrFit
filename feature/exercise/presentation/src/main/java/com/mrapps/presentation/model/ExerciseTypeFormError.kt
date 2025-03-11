package com.mrapps.presentation.model

import com.mrapps.presentation.UiText

sealed interface ExerciseTypeFormError {

    data class Strength(
        val movementType: UiText? = null,
        val muscleGroup: UiText? = null,
        val exerciseGoal: UiText? = null,
    ) : ExerciseTypeFormError

    data class Endurance(
        val duration: UiText? = null,
        val distance: UiText? = null,
        val average: UiText? = null,
    ) : ExerciseTypeFormError
}