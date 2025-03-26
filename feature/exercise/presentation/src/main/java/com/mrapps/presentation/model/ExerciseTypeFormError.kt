package com.mrapps.presentation.model

import com.mrapps.presentation.UiText

sealed interface ExerciseTypeFormError {

    data class Strength(
        val movementType: UiText? = null,
        val muscleGroup: UiText? = null,
        val exerciseGoal: UiText? = null,
    ) : ExerciseTypeFormError

    data class Endurance(
        val activityType: UiText? = null,
        val durationUnit: UiText? = null,
    ) : ExerciseTypeFormError
}