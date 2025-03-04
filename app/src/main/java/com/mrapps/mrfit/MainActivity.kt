package com.mrapps.mrfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mrapps.mrfit.navigation.AppNavigation
import com.mrapps.mrfit.theme.MrFitTheme
import com.mrapps.mrfit.theme.ThemeWithSurface

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MrFitTheme {
                ThemeWithSurface {
                    AppNavigation()
                }
            }
        }
    }
}