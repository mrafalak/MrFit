package com.mrapps.settings.navigation

sealed class SettingsRoutes(val route: String) {
    data object Settings : SettingsRoutes("settings")
}