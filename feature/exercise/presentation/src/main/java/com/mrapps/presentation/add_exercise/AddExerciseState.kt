package com.mrapps.presentation.add_exercise

import com.mrapps.presentation.UiText
import com.mrapps.presentation.model.ExerciseForm

data class AddExerciseState(
    val form: ExerciseForm = ExerciseForm(),
    val isFormValidated: Boolean = false,
    val isTypeFormValidated: Boolean = false,
    val error: UiText? = null,
)