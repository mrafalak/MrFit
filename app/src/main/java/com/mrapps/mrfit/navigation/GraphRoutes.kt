package com.mrapps.mrfit.navigation

sealed class GraphRoutes(val route: String) {
    data object Home : GraphRoutes("home_graph")
}