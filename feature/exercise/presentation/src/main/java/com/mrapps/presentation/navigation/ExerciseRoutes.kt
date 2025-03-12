package com.mrapps.presentation.navigation

sealed class ExerciseRoutes(val route: String) {
    data object AddExercise : ExerciseRoutes("add_exercise")
    data object ExerciseList : ExerciseRoutes("exercise_list")
}