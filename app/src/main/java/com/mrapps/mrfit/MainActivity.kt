package com.mrapps.mrfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mrapps.mrfit.theme.MrFitTheme
import com.mrapps.mrfit.theme.ThemeWithSurface
import com.mrapps.navigation.AppNavigation
import com.mrapps.navigation.FeatureNavGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navGraphs: Set<@JvmSuppressWildcards FeatureNavGraph>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MrFitTheme {
                ThemeWithSurface {
                    AppNavigation(navGraphs)
                }
            }
        }
    }
}