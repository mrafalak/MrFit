package com.mrapps.presentation.add_exercise.exercise_type.endurance

import com.mrapps.presentation.UiText
import com.mrapps.presentation.model.ExerciseTypeForm

data class AddEnduranceExerciseState(
    val form: ExerciseTypeForm.Endurance = ExerciseTypeForm.Endurance(),
    val isFormValidated: Boolean = false,
    val error: UiText? = null,
)