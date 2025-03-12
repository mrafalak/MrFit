package com.mrapps.presentation.manage_exercise

import com.mrapps.presentation.UiText

sealed interface ManageExerciseEvent {
    data object OnSuccess : ManageExerciseEvent
    data class ShowSnackbar(val message: UiText) : ManageExerciseEvent
}