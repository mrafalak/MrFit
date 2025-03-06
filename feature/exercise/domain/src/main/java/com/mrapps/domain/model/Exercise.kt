package com.mrapps.domain.model

import com.mrapps.domain.model.exercise_type.ExerciseType

data class Exercise(
    val id: String,
    val name: String,
    val type: ExerciseType,
    val description: String?
)