package com.mrapps.mrfit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mrapps.mrfit.screen.SummaryScreen

fun NavGraphBuilder.addHomeNavGraph(navController: NavHostController) {
    composable(HomeRoutes.Summary.route) {
        SummaryScreen()
    }
}