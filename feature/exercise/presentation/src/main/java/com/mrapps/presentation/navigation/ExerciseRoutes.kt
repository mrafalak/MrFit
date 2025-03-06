package com.mrapps.presentation.navigation

sealed class ExerciseRoutes(val route: String) {
    data object ExerciseList : ExerciseRoutes("exercise_list")
}