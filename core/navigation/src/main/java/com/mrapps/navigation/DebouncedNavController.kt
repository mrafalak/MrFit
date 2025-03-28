package com.mrapps.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

class DebouncedNavController(
    private val navController: NavController,
    private val debounceTimeMs: Long = 500L
) {
    private var lastNavigationTime = 0L

    private fun canNavigate(): Boolean {
        val now = System.currentTimeMillis()
        return if (now - lastNavigationTime > debounceTimeMs) {
            lastNavigationTime = now
            true
        } else {
            false
        }
    }

    fun navigate(route: String, builder: (NavOptionsBuilder.() -> Unit)? = null) {
        if (!canNavigate()) return
        if (builder != null) {
            navController.navigate(route, builder)
        } else {
            navController.navigate(route)
        }
    }

    fun popBackStack(): Boolean {
        return if (canNavigate()) {
            navController.popBackStack()
        } else false
    }

    fun safePopBackStackOrNavigate(route: String) {
        if (!popBackStack()) {
            navigate(route)
        }
    }

    val currentBackStackEntry get() = navController.currentBackStackEntry
    val previousBackStackEntry get() = navController.previousBackStackEntry
}