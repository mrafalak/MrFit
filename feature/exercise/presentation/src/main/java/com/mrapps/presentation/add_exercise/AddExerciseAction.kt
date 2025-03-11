package com.mrapps.presentation.add_exercise

import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.presentation.model.ExerciseTypeForm

sealed interface AddExerciseAction {
    data object ValidateForm : AddExerciseAction
    data class OnNameChange(val name: String) : AddExerciseAction
    data class OnDescriptionChange(val description: String) : AddExerciseAction
    data class OnTypeChange(val type: ExerciseTypeEnum) : AddExerciseAction
    data class OnTypeFormChange(
        val typeForm: ExerciseTypeForm,
        val isTypeFormValidated: Boolean
    ) : AddExerciseAction

    data object ClearError : AddExerciseAction
    data object NavigateBack : AddExerciseAction
}