package com.mrapps.presentation.navigation


sealed class ExerciseRoutes(val route: String) {
    data object ExerciseList : ExerciseRoutes("exercise_list")
    data object AddExercise : ExerciseRoutes("manageExercise")
    data object EditExercise : ExerciseRoutes("manageExercise?exerciseId={$EXERCISE_ID_KEY}") {
        fun withArgs(exerciseId: String): String {
            return "manageExercise?exerciseId=$exerciseId"
        }
    }

    companion object {
        const val EXERCISE_ID_KEY = "exerciseId"
    }
}