package com.mrapps.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mrapps.navigation.DebouncedBackScreen
import com.mrapps.navigation.DebouncedNavController
import com.mrapps.navigation.FeatureNavGraph
import com.mrapps.navigation.GraphRoutes
import com.mrapps.presentation.manage_exercise.ManageExerciseScreen
import com.mrapps.presentation.exercise_list.ExerciseListScreen
import com.mrapps.presentation.navigation.ExerciseRoutes.Companion.EXERCISE_ID_KEY
import javax.inject.Inject

class ExerciseNavGraph @Inject constructor() : FeatureNavGraph {
    override val graphRoute: GraphRoutes = GraphRoutes.Exercise

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: DebouncedNavController
    ) {
        navGraphBuilder.navigation(
            route = graphRoute.route,
            startDestination = ExerciseRoutes.ExerciseList.route
        ) {
            composable(ExerciseRoutes.ExerciseList.route) {
                ExerciseListScreen(
                    navigateToEditExercise = { exerciseId ->
                        navController.navigate(ExerciseRoutes.EditExercise.withArgs(exerciseId))
                    },
                    navigateToAddExercise = {
                        navController.navigate(ExerciseRoutes.AddExercise.route)
                    }
                )
            }
            composable(ExerciseRoutes.AddExercise.route) {
                DebouncedBackScreen(navController) {
                    ManageExerciseScreen(
                        navigateBack = {
                            navController.safePopBackStackOrNavigate(ExerciseRoutes.ExerciseList.route)
                        }
                    )
                }
            }
            composable(ExerciseRoutes.EditExercise.route) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getString(EXERCISE_ID_KEY)

                DebouncedBackScreen(navController) {
                    ManageExerciseScreen(
                        exerciseId = exerciseId,
                        navigateBack = {
                            navController.safePopBackStackOrNavigate(ExerciseRoutes.ExerciseList.route)
                        }
                    )
                }
            }
        }
    }
}