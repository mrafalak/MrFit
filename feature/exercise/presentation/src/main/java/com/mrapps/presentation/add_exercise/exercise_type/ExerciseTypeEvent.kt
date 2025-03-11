package com.mrapps.presentation.add_exercise.exercise_type


sealed interface ExerciseTypeEvent {
    data object ValidateTypeForm : ExerciseTypeEvent
}