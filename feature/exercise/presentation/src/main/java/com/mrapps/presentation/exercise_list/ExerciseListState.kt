package com.mrapps.presentation.exercise_list

import com.mrapps.domain.model.Exercise
import com.mrapps.presentation.UiText

data class ExerciseListState(
    val exercises: List<Exercise> = emptyList(),
    val error: UiText? = null,
)