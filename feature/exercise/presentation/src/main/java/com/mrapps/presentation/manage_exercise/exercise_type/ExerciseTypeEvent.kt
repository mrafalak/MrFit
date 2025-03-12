package com.mrapps.presentation.manage_exercise.exercise_type


sealed interface ExerciseTypeEvent {
    data object ValidateTypeForm : ExerciseTypeEvent
}