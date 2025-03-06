package com.mrapps.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mrapps.navigation.FeatureNavGraph
import com.mrapps.navigation.GraphRoutes
import com.mrapps.presentation.ExerciseListScreen
import javax.inject.Inject

class ExerciseNavGraph @Inject constructor() : FeatureNavGraph {
    override val graphRoute: GraphRoutes = GraphRoutes.Exercises

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.navigation(
            route = graphRoute.route,
            startDestination = ExerciseRoutes.ExerciseList.route
        ) {
            composable(ExerciseRoutes.ExerciseList.route) {
                ExerciseListScreen()
            }
        }
    }
}