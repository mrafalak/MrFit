package com.mrapps.presentation.exercise_list


sealed interface ExerciseListAction {
    data object NavigateToAddExercise : ExerciseListAction
    data object ClearError : ExerciseListAction
}