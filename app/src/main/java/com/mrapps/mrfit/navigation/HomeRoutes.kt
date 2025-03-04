package com.mrapps.mrfit.navigation

sealed class HomeRoutes(val route: String) {
    data object Summary : HomeRoutes("summary")
}