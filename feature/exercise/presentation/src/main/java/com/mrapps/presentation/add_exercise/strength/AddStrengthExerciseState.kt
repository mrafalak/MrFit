package com.mrapps.presentation.add_exercise.strength

import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.presentation.UiText

data class AddStrengthExerciseState(
    val form: ExerciseType.Strength = ExerciseType.Strength(),
    val error: UiText? = null,
)