package com.mrapps.presentation.model

import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.presentation.UiText

data class ExerciseForm(
    val name: String = "",
    val nameError: UiText? = null,
    val description: String = "",
    val descriptionError: UiText? = null,
    val type: ExerciseTypeEnum = ExerciseTypeEnum.STRENGTH,
    val typeForm: ExerciseTypeForm = ExerciseTypeForm.Strength(),
)