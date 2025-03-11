package com.mrapps.presentation.add_exercise.exercise_type

import com.mrapps.presentation.model.ExerciseTypeForm

sealed class ExerciseTypeAction {

    data class OnTypeFormChange(
        val typeForm: ExerciseTypeForm,
        val isTypeFormValidated: Boolean
    ) : ExerciseTypeAction()

    data object ValidateTypeExerciseType : ExerciseTypeAction()
}