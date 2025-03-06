package com.mrapps.navigation

sealed class GraphRoutes(val route: String) {
    data object Home : GraphRoutes("home_graph")
    data object Settings : GraphRoutes("settings_graph")
    data object Exercises : GraphRoutes("exercises_graph")
}