package com.mrapps.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(navGraphs: Set<FeatureNavGraph>) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = GraphRoutes.Exercise.route
    ) {
        navGraphs.forEach { navGraph ->
            navGraph.registerGraph(this, navController)
        }
    }
}