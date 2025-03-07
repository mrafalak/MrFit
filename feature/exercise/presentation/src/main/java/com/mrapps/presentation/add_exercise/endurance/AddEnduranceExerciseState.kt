package com.mrapps.presentation.add_exercise.endurance

import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.presentation.UiText

data class AddEnduranceExerciseState(
    val form: ExerciseType.Endurance = ExerciseType.Endurance(),
    val error: UiText? = null,
)