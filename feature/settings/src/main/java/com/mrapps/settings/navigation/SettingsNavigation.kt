package com.mrapps.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mrapps.navigation.DebouncedNavController
import com.mrapps.navigation.FeatureNavGraph
import com.mrapps.navigation.GraphRoutes
import com.mrapps.settings.SettingsScreen
import javax.inject.Inject

class SettingsNavGraph @Inject constructor() : FeatureNavGraph {
    override val graphRoute: GraphRoutes = GraphRoutes.Settings

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: DebouncedNavController
    ) {
        navGraphBuilder.navigation(
            route = graphRoute.route,
            startDestination = SettingsRoutes.Settings.route
        ) {
            composable(SettingsRoutes.Settings.route) {
                SettingsScreen()
            }
        }
    }
}