package com.mrapps.presentation.add_exercise

import com.mrapps.presentation.UiText
import com.mrapps.presentation.model.ExerciseForm

data class AddExerciseState(
    val form: ExerciseForm = ExerciseForm(),
    val error: UiText? = null,
)