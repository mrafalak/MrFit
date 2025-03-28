package com.mrapps.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun DebouncedBackScreen(
    navController: DebouncedNavController,
    content: @Composable () -> Unit
) {
    BackHandler {
        navController.popBackStack()
    }
    content()
}