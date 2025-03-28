package com.mrapps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(navGraphs: Set<FeatureNavGraph>) {
    val navController = rememberNavController()
    val debouncedNavController = remember { DebouncedNavController(navController) }

    NavHost(
        navController = navController,
        startDestination = GraphRoutes.Exercise.route
    ) {
        navGraphs.forEach { navGraph ->
            navGraph.registerGraph(this, debouncedNavController)
        }
    }
}