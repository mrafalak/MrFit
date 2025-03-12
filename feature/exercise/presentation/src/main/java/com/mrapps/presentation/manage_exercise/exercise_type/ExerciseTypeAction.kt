package com.mrapps.presentation.manage_exercise.exercise_type

import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.presentation.model.ExerciseTypeForm

sealed class ExerciseTypeAction {
    data class SetInitialTypeForm(val type: ExerciseType) : ExerciseTypeAction()
    data class OnTypeFormChange(
        val typeForm: ExerciseTypeForm,
        val isTypeFormValidated: Boolean
    ) : ExerciseTypeAction()

    data object ValidateTypeExerciseType : ExerciseTypeAction()
}