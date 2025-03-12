package com.mrapps.presentation.manage_exercise

import com.mrapps.presentation.UiText
import com.mrapps.presentation.model.ExerciseForm

data class ManageExerciseState(
    val form: ExerciseForm = ExerciseForm(),
    val isFormValidated: Boolean = false,
    val isTypeFormValidated: Boolean = false,
    val error: UiText? = null,
)