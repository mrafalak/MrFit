package com.mrapps.presentation.manage_exercise.exercise_type

import com.mrapps.domain.model.exercise_type.ExerciseType

sealed interface ExerciseTypeEvent {
    data class SetInitialTypeForm(val type: ExerciseType) : ExerciseTypeEvent
    data object ValidateTypeForm : ExerciseTypeEvent
}