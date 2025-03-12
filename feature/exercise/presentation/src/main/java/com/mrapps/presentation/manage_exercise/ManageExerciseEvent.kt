package com.mrapps.presentation.manage_exercise

import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.presentation.UiText

sealed interface ManageExerciseEvent {
    data class SetInitialTypeForm(val type: ExerciseType) : ManageExerciseEvent
    data object OnSuccess : ManageExerciseEvent
    data class ShowSnackbar(val message: UiText) : ManageExerciseEvent
}