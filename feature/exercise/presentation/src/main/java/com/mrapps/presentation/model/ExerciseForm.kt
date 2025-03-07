package com.mrapps.presentation.model

import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.model.exercise_type.ExerciseType

data class ExerciseForm(
    val name: String = "",
    val description: String = "",
    val type: ExerciseTypeEnum = ExerciseTypeEnum.STRENGTH,
    val typeForm: ExerciseType = ExerciseType.Strength(),
)