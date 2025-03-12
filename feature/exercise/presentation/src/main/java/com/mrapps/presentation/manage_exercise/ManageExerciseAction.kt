package com.mrapps.presentation.manage_exercise

import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.presentation.model.ExerciseTypeForm

sealed interface ManageExerciseAction {
    data object ValidateForm : ManageExerciseAction
    data class OnNameChange(val name: String) : ManageExerciseAction
    data class OnDescriptionChange(val description: String) : ManageExerciseAction
    data class OnTypeChange(val type: ExerciseTypeEnum) : ManageExerciseAction
    data class OnTypeFormChange(
        val typeForm: ExerciseTypeForm,
        val isTypeFormValidated: Boolean
    ) : ManageExerciseAction

    data object ClearError : ManageExerciseAction
    data object NavigateBack : ManageExerciseAction
}