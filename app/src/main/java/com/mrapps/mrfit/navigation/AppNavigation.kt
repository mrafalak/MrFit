package com.mrapps.mrfit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = GraphRoutes.Home.route) {
        navigation(
            route = GraphRoutes.Home.route,
            startDestination = HomeRoutes.Summary.route
        ) {
            addHomeNavGraph(navController)
        }
    }
}