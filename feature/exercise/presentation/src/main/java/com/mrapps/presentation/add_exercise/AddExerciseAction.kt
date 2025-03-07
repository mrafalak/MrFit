package com.mrapps.presentation.add_exercise

import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.model.exercise_type.ExerciseType

sealed interface AddExerciseAction {
    data object SaveNewExercise : AddExerciseAction
    data class OnNameChange(val name: String) : AddExerciseAction
    data class OnDescriptionChange(val description: String) : AddExerciseAction
    data class OnTypeChange(val type: ExerciseTypeEnum) : AddExerciseAction
    data class OnTypeFormChange(val typeForm: ExerciseType) : AddExerciseAction
    data object ClearError : AddExerciseAction
    data object NavigateBack : AddExerciseAction
}