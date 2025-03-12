package com.mrapps.presentation.manage_exercise.exercise_type.strength

import com.mrapps.presentation.UiText
import com.mrapps.presentation.model.ExerciseTypeForm

data class ManageStrengthExerciseState(
    val form: ExerciseTypeForm.Strength = ExerciseTypeForm.Strength(),
    val isFormValidated: Boolean = false,
    val error: UiText? = null,
)