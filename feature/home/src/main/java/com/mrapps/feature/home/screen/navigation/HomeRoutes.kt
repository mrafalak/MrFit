package com.mrapps.feature.home.screen.navigation

sealed class HomeRoutes(val route: String) {
    data object Home : HomeRoutes("home")
}