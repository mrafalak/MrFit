package com.mrapps.presentation.add_exercise.exercise_type

import com.mrapps.presentation.model.ExerciseTypeForm

data class ExerciseTypeState(
    val form: ExerciseTypeForm = ExerciseTypeForm.Strength(),
    val isTypeFormValidated: Boolean = false,
)