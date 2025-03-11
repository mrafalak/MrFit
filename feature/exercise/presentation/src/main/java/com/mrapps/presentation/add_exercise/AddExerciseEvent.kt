package com.mrapps.presentation.add_exercise

import com.mrapps.presentation.UiText

sealed interface AddExerciseEvent {
    data object OnSuccess : AddExerciseEvent
    data class ShowSnackbar(val message: UiText) : AddExerciseEvent
}