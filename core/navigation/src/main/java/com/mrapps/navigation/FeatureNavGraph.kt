package com.mrapps.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface FeatureNavGraph {
    val graphRoute: GraphRoutes
    fun registerGraph(navGraphBuilder: NavGraphBuilder, navController: NavController)
}