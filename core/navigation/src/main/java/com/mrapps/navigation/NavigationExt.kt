package com.mrapps.navigation

import androidx.navigation.NavController

fun NavController.safePopBackStackOrNavigate(route: String) {
    val popped = popBackStack()
    if (!popped) {
        navigate(route) {
            popUpTo(graph.startDestinationId) { inclusive = true }
        }
    }
}