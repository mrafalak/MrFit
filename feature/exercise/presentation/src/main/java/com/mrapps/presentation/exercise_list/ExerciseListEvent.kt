package com.mrapps.presentation.exercise_list

import com.mrapps.presentation.UiText

sealed interface ExerciseListEvent {
    data class ShowSnackbar(val message: UiText) : ExerciseListEvent
}