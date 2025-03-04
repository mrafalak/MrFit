package com.mrapps.feature.home.screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mrapps.feature.home.screen.HomeScreen
import com.mrapps.navigation.FeatureNavGraph
import com.mrapps.navigation.GraphRoutes
import javax.inject.Inject

class HomeNavGraph @Inject constructor() : FeatureNavGraph {
    override val graphRoute: GraphRoutes = GraphRoutes.Home

    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.navigation(
            route = graphRoute.route,
            startDestination = HomeRoutes.Home.route
        ) {
            composable(HomeRoutes.Home.route) {
                HomeScreen()
            }
        }
    }
}