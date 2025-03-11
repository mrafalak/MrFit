package com.mrapps.presentation.add_exercise.exercise_type.strength

import com.mrapps.presentation.UiText
import com.mrapps.presentation.model.ExerciseTypeForm

data class AddStrengthExerciseState(
    val form: ExerciseTypeForm.Strength = ExerciseTypeForm.Strength(),
    val isFormValidated: Boolean = false,
    val error: UiText? = null,
)